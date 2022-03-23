package com.sergigp.module.weather.domain

import com.sergigp.module.weather.application.FindWeatherError.FindWeatherError
import zio.IO

trait WeatherRepository {
  def search(lat: Double, long: Double): IO[FindWeatherError, Weather]
}
