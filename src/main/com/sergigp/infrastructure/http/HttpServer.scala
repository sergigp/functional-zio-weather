package com.sergigp.infrastructure.http

import com.sergigp.infrastructure.config.AppConfig
import com.sergigp.infrastructure.http.routes.HealthCheckGetController
import com.sergigp.module.weather.infrastructure.controller.WeatherRoutes
import zhttp.service.Server
import zio._
import zio.blocking.Blocking

class HttpServer(
  appConfig: AppConfig,
  weatherRoutes: WeatherRoutes
) {
  def start(): ZIO[Blocking, Throwable, Nothing] =
    Server.start(
      appConfig.httpPort,
      HealthCheckGetController.routes ++ weatherRoutes.routes
    )
}

object HttpServer {
  lazy val live: ZLayer[Has[WeatherRoutes] with Has[AppConfig], Nothing, Has[HttpServer]] = (for {
    appConfig     <- ZIO.service[AppConfig]
    weatherRoutes <- ZIO.service[WeatherRoutes]
  } yield new HttpServer(appConfig, weatherRoutes)).toLayer
}
