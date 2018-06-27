package templates

import scalatags.Text.TypedTag
import scalatags.Text.all._

object HeaderT {
  def apply: TypedTag[String] = header(cls := "main")(
    h1("Hey! I'm Miguel"),
    p(cls := "description")("I'm a computer scientist from Barcelona. I'm passionate about FP (mostly Scala), data, algorithms and mountains.")
  )
}
