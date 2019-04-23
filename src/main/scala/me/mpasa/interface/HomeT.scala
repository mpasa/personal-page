package me.mpasa.interface

import me.mpasa.ReverseRouter
import me.mpasa.domain.model.ArticleRepository
import me.mpasa.interface.components.LayoutOptions
import mouse.boolean.booleanSyntaxMouse
import scalatags.Text.TypedTag
import scalatags.Text.all._

class HomeT(layout: PageT, reverseRouter: ReverseRouter, articleRepository: ArticleRepository) {

  // Number of the last articles to be shown
  val NUM_ARTICLES = 5

  /** Homepage template */
  def apply: TypedTag[String] = layout(LayoutOptions("Home")) {
    div(cls := "wrapper")(
      articleRepository.all.nonEmpty.option(articleRepository.all).map { articles =>
        div(
          h2("Last articles"),
          ul(
            articles.sortBy(_.metadata.published)(Ordering.by(-_.toEpochDay)).take(NUM_ARTICLES).map { article =>
              li(a(href := article.url, article.metadata.title))
            }
          ),
          p(
            "You can find all of them in the ",
            a(href := reverseRouter.archives, "archives"),
            " or grouped by ",
            a(href := reverseRouter.tags, "tag"),
            "."
          )
        )
      }
    )
  }
}
