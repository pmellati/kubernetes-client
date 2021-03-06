package com.goyeau.kubernetes.client.util

import cats.Applicative
import cats.effect.Sync
import io.circe.{Decoder, Encoder, Json, Printer}
import org.http4s.{EntityDecoder, EntityEncoder}
import org.http4s.circe.CirceInstances

private[client] object CirceEntityCodec extends CirceInstances {
  val defaultPrinter: Printer = Printer.noSpaces.copy(dropNullValues = true)
  def jsonDecoder[F[_]: Sync]: EntityDecoder[F, Json] = CirceInstances.defaultJsonDecoder

  implicit def circeEntityEncoder[F[_]: Applicative, A: Encoder]: EntityEncoder[F, A] = jsonEncoderOf[F, A]
  implicit def circeEntityDecoder[F[_]: Sync, A: Decoder]: EntityDecoder[F, A] = jsonOf[F, A]
}
