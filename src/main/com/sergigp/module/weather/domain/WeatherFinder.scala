package com.sergigp.module.weather.domain

import com.sergigp.module.weather.application.FindWeatherError.FindWeatherError
import zio._

class WeatherFinder(repository: WeatherRepository) {
  def find(lat: Double, long: Double): IO[FindWeatherError, Weather] = repository.search(lat, long)
}
