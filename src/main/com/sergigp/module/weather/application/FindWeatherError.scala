package com.sergigp.module.weather.application

object FindWeatherError {
  sealed trait FindWeatherError extends Throwable

  case class LocationNotFoundError(lat: Double, long: Double) extends FindWeatherError
  case class UnexpectedErrorFindingWeather(error: String)     extends FindWeatherError
}
