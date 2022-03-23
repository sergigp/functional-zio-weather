package com.sergigp.module.weather.infrastructure.controller

import com.sergigp.module.weather.application.FindWeatherHandler
import zhttp.http._
import zio.{Has, URLayer, ZIO}

class WeatherRoutes(
  findWeatherHandler: FindWeatherHandler
) {
  val routes: Http[Any, Nothing, Request, Response] = Http.collectZIO[Request] {
    case request @ Method.GET -> !! / "weather" =>
      val queryParams = for {
        lat  <- request.url.queryParams.get("lat").map(_.mkString.toDouble)
        long <- request.url.queryParams.get("long").map(_.mkString.toDouble)
      } yield (lat, long)

      queryParams.fold(
        ZIO.succeed(Response.text("Unable to parse lat and long").setStatus(Status.BAD_REQUEST))
      ) {
        case (lat, long) =>
          findWeatherHandler
            .handle(lat, long)
            .fold(
              error => Response.text(s"Error $error happened").setStatus(Status.INTERNAL_SERVER_ERROR),
              response => Response.text(response.temperature.toString)
            )
      }
  }
}

object WeatherRoutes {
  val live: URLayer[Has[FindWeatherHandler], Has[WeatherRoutes]] = (for {
    handler <- ZIO.service[FindWeatherHandler]
  } yield new WeatherRoutes(handler)).toLayer
}
