package controllers

import controllers.articles.Articles
import scalatags.Text.TypedTag
import scalatags.Text.all._
import templates.{LayoutOptions, PageT}

object Home {

  /** A fancy way of getting icons */
  private def icon(iconClass: String) = span(cls := s"icon $iconClass")

  def apply: TypedTag[String] = PageT(LayoutOptions("Miguel Pérez Pasalodos", Seq("home")))(
    div(cls := "social")(
      a(href := s"mailto:miguel.perez.pasalodos@gmail.com")(icon("fas fa-envelope")),
      a(href := "https://twitter.com/Kamugo")(icon(("fab fa-twitter"))),
      a(href := "https://github.com/mpasa")(icon(("fab fa-github"))),
      a(href := "https://www.linkedin.com/in/miguel-perez-pasalodos")(icon(("fab fa-linkedin")))
    ),
    div(
      h2("Articles"),
      Articles.all.sortBy(_.metadata.published)(Ordering.by(-_.toEpochDay)).map { article =>
        a(href := article.url, article.metadata.title)
      }
    )
  )
}
