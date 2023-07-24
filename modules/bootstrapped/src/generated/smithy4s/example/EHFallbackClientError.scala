package smithy4s.example

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

final case class EHFallbackClientError(message: Option[java.lang.String] = None) extends Throwable {
  override def getMessage(): scala.Predef.String = message.orNull
}
object EHFallbackClientError extends ShapeTag.Companion[EHFallbackClientError] {
  val id: ShapeId = ShapeId("smithy4s.example", "EHFallbackClientError")

  val hints: Hints = Hints(
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[EHFallbackClientError] = struct(
    string.optional[EHFallbackClientError]("message", _.message),
  ){
    EHFallbackClientError.apply
  }.withId(id).addHints(hints)
}
