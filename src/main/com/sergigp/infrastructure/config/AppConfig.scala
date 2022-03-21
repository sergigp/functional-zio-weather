package com.sergigp.infrastructure.config

import zio.{system, Has, IO, ZIO, ZLayer}
import zio.config._
import zio.config.ConfigDescriptor._
import zio.config.typesafe.TypesafeConfigSource

case class AppConfig(weatherApiBaseUrl: String, weatherApiKey: String, httpPort: Int)

object AppConfig {
  private val source: ZIO[system.System, ReadError[String], ConfigSource] = for {
    hoconFile <- ZIO.fromEither(TypesafeConfigSource.fromHoconFile(new java.io.File("resources/application.conf")))
    systemEnv <- ConfigSource.fromSystemEnv
    source = systemEnv <> hoconFile
  } yield source

  private val descriptor: ConfigDescriptor[AppConfig] = (
    string("weather-api-base-url") |@|
    string("OPEN_WEATHER_API_KEY") |@|
    int("http-port")
  )(AppConfig.apply, AppConfig.unapply)

  val live: ZLayer[system.System, ReadError[String], Has[AppConfig]] = (for {
    source <- source
    config <- ZIO.fromEither(read(descriptor.from(source)))
  } yield config).toLayer
}
