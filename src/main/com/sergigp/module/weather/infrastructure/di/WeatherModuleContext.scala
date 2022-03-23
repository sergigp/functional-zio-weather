package com.sergigp.module.weather.infrastructure.di

import com.sergigp.infrastructure.config.AppConfig
import com.sergigp.module.weather.application.FindWeatherHandler
import com.sergigp.module.weather.domain.{WeatherFinder, WeatherRepository}
import com.sergigp.module.weather.infrastructure.controller.WeatherRoutes
import com.sergigp.module.weather.infrastructure.http.WeatherRepositoryLive
import zio.{Has, URLayer, ZIO}

object WeatherModuleContext {
  val weatherRepository: URLayer[Has[AppConfig], Has[WeatherRepository]] =
    (for {
      config <- ZIO.service[AppConfig]
    } yield new WeatherRepositoryLive(config)).toLayer

  val weatherFinder: URLayer[Has[WeatherRepository], Has[WeatherFinder]] =
    (for {
      repository <- ZIO.service[WeatherRepository]
    } yield new WeatherFinder(repository)).toLayer

  val findWeatherHandler: URLayer[Has[WeatherFinder], Has[FindWeatherHandler]] = (for {
    finder <- ZIO.service[WeatherFinder]
  } yield new FindWeatherHandler(finder)).toLayer

  val findWeatherRoute: URLayer[Has[FindWeatherHandler], Has[WeatherRoutes]] = (for {
    handler <- ZIO.service[FindWeatherHandler]
  } yield new WeatherRoutes(handler)).toLayer

  val findWeatherUseCase = weatherRepository >>> weatherFinder >>> findWeatherHandler >>> findWeatherRoute
}
