package com.sergigp.module.weather.infrastructure.http

import com.sergigp.infrastructure.config.AppConfig
import com.sergigp.module.weather.application.FindWeatherError.{FindWeatherError, UnexpectedErrorFindingWeather}
import com.sergigp.module.weather.domain.{Weather, WeatherRepository}
import com.sergigp.module.weather.infrastructure.marshaller.WeatherDecoder._
import sttp.client3.{basicRequest, UriContext}
import sttp.client3.asynchttpclient.zio.AsyncHttpClientZioBackend
import sttp.client3.circe._
import zio.{IO, ZIO}

class WeatherRepositoryLive(appConfig: AppConfig) extends WeatherRepository {
  override def search(lat: Double, long: Double): IO[FindWeatherError, Weather] =
    (for {
      backend <- AsyncHttpClientZioBackend()
      request = basicRequest
        .get(
          uri"${appConfig.weatherApiBaseUrl}weather?lat=$lat&lon=$long&appid=${appConfig.weatherApiKey}&units=metric"
        )
        .response(asJson[Weather])
      response <- backend.send(request)
      weather  <- ZIO.fromEither(response.body)
    } yield weather).mapError(error => UnexpectedErrorFindingWeather(error.getMessage))
}
