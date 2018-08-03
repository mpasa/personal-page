package me.mpasa.templates

import me.mpasa.controllers.articles.Articles
import me.mpasa.Router
import mouse.boolean.booleanSyntaxMouse
import scalatags.Text.TypedTag
import scalatags.Text.all._
import me.mpasa.templates.components.LayoutOptions

object HomeT {

  // Number of the last articles to be shown
  val NUM_ARTICLES = 5

  /** Homepage template */
  def apply: TypedTag[String] = PageT(LayoutOptions("Home")) {
    div(cls := "wrapper")(
      Articles.all.nonEmpty.option(Articles.all).map { articles =>
        div(
          h2("Last articles"),
          ul(
            articles.sortBy(_.metadata.published)(Ordering.by(-_.toEpochDay)).take(NUM_ARTICLES).map { article =>
              li(a(href := article.url, article.metadata.title))
            }
          ),
          p(
            "You can find all of them in the ", a(href := Router.Reverse.archives, "archives"), " or grouped by ",
            a(href := Router.Reverse.tags, "tag"), "."
          )
        )
      }
    )
  }
}
