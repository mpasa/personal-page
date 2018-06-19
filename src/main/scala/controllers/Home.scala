package controllers

import scalatags.Text.TypedTag
import scalatags.Text.all._
import templates.{LayoutOptions, LayoutT}

object Home {

  /** A fancy way of getting icons */
  private def icon(iconClass: String) = span(cls := s"icon $iconClass")

  def apply: TypedTag[String] = LayoutT(LayoutOptions("Miguel Pérez Pasalodos"))(
    h1("Hey! I'm Miguel"),
    p(cls := "description")("I'm a computer scientist from Barcelona. I'm passionate about FP (mostly Scala), data, algorithms and mountains."),
    div(cls := "social")(
      a(href := s"mailto:miguel.perez.pasalodos@gmail.com")(icon("fas fa-envelope")),
      a(href := "https://twitter.com/Kamugo")(icon(("fab fa-twitter"))),
      a(href := "https://github.com/mpasa")(icon(("fab fa-github"))),
      a(href := "https://www.linkedin.com/in/miguel-perez-pasalodos")(icon(("fab fa-linkedin")))
    )
  )
}
