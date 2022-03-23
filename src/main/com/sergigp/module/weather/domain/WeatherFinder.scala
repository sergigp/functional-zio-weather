package com.sergigp.module.weather.domain

import com.sergigp.module.weather.application.FindWeatherError.FindWeatherError
import com.sergigp.module.weather.infrastructure.http.WeatherRepository
import zio._

trait WeatherFinder {
  def find(lat: Double, long: Double): IO[FindWeatherError, Weather]
}

object WeatherFinder {
  val live: URLayer[Has[WeatherRepository], Has[WeatherFinder]] =
    (for {
      repository <- ZIO.service[WeatherRepository]
    } yield WeatherFinderLive(repository)).toLayer
}

case class WeatherFinderLive(repository: WeatherRepository) extends WeatherFinder {
  override def find(lat: Double, long: Double): IO[FindWeatherError, Weather] = repository.search(lat, long)
}
