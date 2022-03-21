package com.sergigp.infrastructure.http

import com.sergigp.infrastructure.config.AppConfig
import com.sergigp.infrastructure.http.routes.HealthCheck
import zhttp.service.Server
import zio._
import zio.blocking.Blocking

class HttpServer(
  appConfig: AppConfig
) {
  def start(): ZIO[Blocking, Throwable, Nothing] =
    Server.start(
      appConfig.httpPort,
      HealthCheck.routes
    )
}

object HttpServer {
  lazy val live: URLayer[Has[AppConfig], Has[HttpServer]] = (for {
    appConfig <- ZIO.service[AppConfig]
  } yield new HttpServer(appConfig)).toLayer
}
