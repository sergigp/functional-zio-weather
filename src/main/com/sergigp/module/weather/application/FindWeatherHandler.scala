package com.sergigp.module.weather.application

import com.sergigp.module.weather.application.FindWeatherError.FindWeatherError
import com.sergigp.module.weather.domain.WeatherFinder
import zio.IO

class FindWeatherHandler(finder: WeatherFinder) {
  def handle(lat: Double, long: Double): IO[FindWeatherError, WeatherResponse] =
    finder
      .find(lat, long)
      .map(weather => WeatherResponse(weather.temperature))
}
