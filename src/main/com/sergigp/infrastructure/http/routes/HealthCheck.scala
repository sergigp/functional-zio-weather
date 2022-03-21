package com.sergigp.infrastructure.http.routes

import zhttp.http._

object HealthCheck {
  val routes = Http.collect[Request] {
    case Method.GET -> !! / "health-check" / "app" =>
      Response.ok
  }
}
