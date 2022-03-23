package com.sergigp

import com.sergigp.infrastructure.config.AppConfig
import com.sergigp.infrastructure.http.HttpServer
import com.sergigp.module.weather.application.FindWeatherHandler
import com.sergigp.module.weather.domain.WeatherFinder
import com.sergigp.module.weather.infrastructure.controller.WeatherRoutes
import com.sergigp.module.weather.infrastructure.http.WeatherRepository
import zio.{ExitCode, URIO, ZIO, _}
import zio.magic._

object Starter extends zio.App {
  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    (for {
      httpServer <- ZIO.service[HttpServer]
      _          <- httpServer.start()
    } yield ())
      .inject(
        ZEnv.live,
        AppConfig.live,
        HttpServer.live,
        WeatherRoutes.live,
        FindWeatherHandler.live,
        WeatherFinder.live,
        WeatherRepository.live
      )
      .exitCode
}
