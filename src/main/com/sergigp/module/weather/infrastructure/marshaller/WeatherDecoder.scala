package com.sergigp.module.weather.infrastructure.marshaller

import com.sergigp.module.weather.domain.Weather
import io.circe.{Decoder, HCursor}
import io.circe.Decoder.Result

object WeatherDecoder {
  implicit val weatherDecoder = new Decoder[Weather] {
    override def apply(c: HCursor): Result[Weather] =
      for {
        fahrenheit <- c.downField("main").downField("temp").as[Double]
      } yield Weather(fahrenheit)
  }
}
