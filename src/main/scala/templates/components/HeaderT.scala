package templates.components

import me.mpasa.Router
import scalatags.Text.TypedTag
import scalatags.Text.all._

/** Header of the page
  * It contains a title, short description and link to different sections
  */
object HeaderT {
  def apply: TypedTag[String] = header(
    div(cls := "wrapper")(
      div(cls := "title")(
        a(href := "/")("Hey! I'm Miguel")
      ),
      div(cls := "description wrapper") {
        "I'm a computer scientist from Barcelona. I'm passionate about FP (mostly Scala), data, algorithms and mountains."
      },
      div(cls := "menu flex spaceBetween")(
        div(cls := "nav")(
          a(cls := "button", href := Router.Reverse.about, "About me"),
        ),
        SocialT.socialButtons
      )
    )
  )
}
