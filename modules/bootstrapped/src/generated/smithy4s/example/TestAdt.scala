package smithy4s.example

import smithy4s.ByteArray
import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.optics.Lens
import smithy4s.optics.Prism
import smithy4s.schema.Schema.bytes
import smithy4s.schema.Schema.int
import smithy4s.schema.Schema.long
import smithy4s.schema.Schema.short
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct
import smithy4s.schema.Schema.union

sealed trait TestAdt extends AdtMixinOne with AdtMixinTwo with scala.Product with scala.Serializable {
  @inline final def widen: TestAdt = this
}
object TestAdt extends ShapeTag.Companion[TestAdt] {
  val id: ShapeId = ShapeId("smithy4s.example", "TestAdt")

  val hints: Hints = Hints.empty

  object Prisms {
    val one = Prism.partial[TestAdt, AdtOne]{ case t: AdtOne => t }(identity)
    val two = Prism.partial[TestAdt, AdtTwo]{ case t: AdtTwo => t }(identity)
  }

  final case class AdtOne(lng: Option[Long] = None, sht: Option[Short] = None, blb: Option[ByteArray] = None, str: Option[String] = None) extends TestAdt with AdtMixinThree
  object AdtOne extends ShapeTag.Companion[AdtOne] {
    val id: ShapeId = ShapeId("smithy4s.example", "AdtOne")

    val hints: Hints = Hints.empty

    object Lenses {
      val lng = Lens[AdtOne, Option[Long]](_.lng)(n => a => a.copy(lng = n))
      val sht = Lens[AdtOne, Option[Short]](_.sht)(n => a => a.copy(sht = n))
      val blb = Lens[AdtOne, Option[ByteArray]](_.blb)(n => a => a.copy(blb = n))
      val str = Lens[AdtOne, Option[String]](_.str)(n => a => a.copy(str = n))
    }

    val schema: Schema[AdtOne] = struct(
      long.optional[AdtOne]("lng", _.lng),
      short.optional[AdtOne]("sht", _.sht),
      bytes.optional[AdtOne]("blb", _.blb),
      string.optional[AdtOne]("str", _.str),
    ){
      AdtOne.apply
    }.withId(id).addHints(hints)

    val alt = schema.oneOf[TestAdt]("one")
  }
  final case class AdtTwo(lng: Option[Long] = None, sht: Option[Short] = None, int: Option[Int] = None) extends TestAdt
  object AdtTwo extends ShapeTag.Companion[AdtTwo] {
    val id: ShapeId = ShapeId("smithy4s.example", "AdtTwo")

    val hints: Hints = Hints.empty

    object Lenses {
      val lng = Lens[AdtTwo, Option[Long]](_.lng)(n => a => a.copy(lng = n))
      val sht = Lens[AdtTwo, Option[Short]](_.sht)(n => a => a.copy(sht = n))
      val int = Lens[AdtTwo, Option[Int]](_.int)(n => a => a.copy(int = n))
    }

    val schema: Schema[AdtTwo] = struct(
      long.optional[AdtTwo]("lng", _.lng),
      short.optional[AdtTwo]("sht", _.sht),
      int.optional[AdtTwo]("int", _.int),
    ){
      AdtTwo.apply
    }.withId(id).addHints(hints)

    val alt = schema.oneOf[TestAdt]("two")
  }


  implicit val schema: Schema[TestAdt] = union(
    AdtOne.alt,
    AdtTwo.alt,
  ){
    case c: AdtOne => AdtOne.alt(c)
    case c: AdtTwo => AdtTwo.alt(c)
  }.withId(id).addHints(hints)
}
