package controllers

import controllers.articles.Articles
import mouse.boolean.booleanSyntaxMouse
import scalatags.Text.TypedTag
import scalatags.Text.all._
import templates.{LayoutOptions, PageT}

object Home {

  /** A fancy way of getting icons */
  private def icon(iconClass: String) = span(cls := s"icon $iconClass")

  def apply: TypedTag[String] = PageT(LayoutOptions("Miguel Pérez Pasalodos", Seq("home"))) {
    div(
      div(cls := "social")(
        a(href := s"mailto:miguel.perez.pasalodos@gmail.com")(icon("fas fa-envelope")),
        a(href := "https://twitter.com/Kamugo")(icon(("fab fa-twitter"))),
        a(href := "https://github.com/mpasa")(icon(("fab fa-github"))),
        a(href := "https://www.linkedin.com/in/miguel-perez-pasalodos")(icon(("fab fa-linkedin")))
      )
      ,
      Articles.all.nonEmpty.option(Articles.all).map { articles =>
        div(
          h2("Articles"),
          articles.sortBy(_.metadata.published)(Ordering.by(-_.toEpochDay)).map { article =>
            a(href := article.url, article.metadata.title)
          }
        )
      }
    )
  }
}
