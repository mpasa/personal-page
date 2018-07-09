package templates.components

import me.mpasa.Router
import scalatags.Text.all._

object SocialT {
  /** A fancy way of getting icons */
  private def icon(iconClass: String) = span(cls := s"icon $iconClass")

  val socialButtons = div(cls := "social")(
    a(cls := "mail", href := s"mailto:miguel.perez.pasalodos@gmail.com")(icon("fas fa-envelope")),
    a(cls := "twitter", href := "https://twitter.com/Kamugo")(icon(("fab fa-twitter"))),
    a(cls := "github", href := "https://github.com/mpasa")(icon(("fab fa-github"))),
    a(cls := "linkedin", href := "https://www.linkedin.com/in/miguel-perez-pasalodos")(icon(("fab fa-linkedin"))),
    a(cls := "rss", href := Router.Reverse.feed)(icon(("fas fa-rss")))
  )
}
