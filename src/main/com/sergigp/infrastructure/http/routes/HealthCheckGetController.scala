package com.sergigp.infrastructure.http.routes

import zhttp.http._

object HealthCheckGetController {
  val routes: Http[Any, Nothing, Request, Response] = Http.collect[Request] {
    case Method.GET -> !! / "health-check" / "app" =>
      Response.ok
  }
}
