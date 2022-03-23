package com.sergigp.module.weather.infrastructure.http

import com.sergigp.infrastructure.config.AppConfig
import com.sergigp.module.weather.application.FindWeatherError.{FindWeatherError, UnexpectedErrorFindingWeather}
import com.sergigp.module.weather.domain.Weather
import com.sergigp.module.weather.infrastructure.marshaller.WeatherDecoder._
import sttp.client3.{basicRequest, UriContext}
import sttp.client3.asynchttpclient.zio.AsyncHttpClientZioBackend
import sttp.client3.circe._
import zio.{Has, IO, URLayer, ZIO}

trait WeatherRepository {
  def search(lat: Double, long: Double): IO[FindWeatherError, Weather]
}

object WeatherRepository {
  val live: URLayer[Has[AppConfig], Has[WeatherRepository]] =
    (for {
      config <- ZIO.service[AppConfig]
    } yield WeatherRepositoryLive(config)).toLayer
}

case class WeatherRepositoryLive(appConfig: AppConfig) extends WeatherRepository {
  override def search(lat: Double, long: Double): IO[FindWeatherError, Weather] =
    (for {
      backend <- AsyncHttpClientZioBackend()
      request = basicRequest
        .get(
          uri"https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$long&appid=${appConfig.weatherApiKey}&units=metric"
        )
        .response(asJson[Weather])
      response <- backend.send(request)
      weather  <- ZIO.fromEither(response.body)
    } yield weather).mapError(error => UnexpectedErrorFindingWeather(error.getMessage))
}
