package me.mpasa.templates

import me.mpasa.ReverseRouter
import me.mpasa.controllers.articles.Article
import me.mpasa.templates.components.LayoutOptions
import scalatags.Text.TypedTag
import scalatags.Text.all._

/** Templates to show different post archives */
class ArchivesT(layout: PageT, reverseRouter: ReverseRouter) {

  /** Shows a list of all the published posts */
  def all(options: LayoutOptions, articles: Seq[Article]): TypedTag[String] = layout(options) {
    div(cls := "wrapper")(
      h1("Archives"),
      p(
        "Here you can find all the articles I've written, sorted by date in descending order. ",
        "You can also check out a list of them grouped by ", a(href := reverseRouter.tags, "tag"), "."
      ),
      ul(
        articles.sortBy(_.metadata.published)(Ordering.by(-_.toEpochDay)).map { article =>
          li(
            a(
              href := reverseRouter.article(article),
              article.metadata.title
            )
          )
        }
      )
    )
  }

  /** Shows a list of all the tags */
  def tags(options: LayoutOptions, tags: Seq[String]): TypedTag[String] = layout(options) {
    div(cls := "wrapper")(
      h1("Tags"),
      p(
        "Here you can find all the tags I've written about, sorted alphanumerically in ascending order. ",
        "You can also check out a list of ", a(href := reverseRouter.archives, "all the posts"), "."
      ),
      ul(
        tags.sorted.map { tag =>
          li(
            a(
              href := reverseRouter.tag(tag),
              tag
            )
          )
        }
      )
    )
  }

  /** Shows a list of all the tags */
  def tag(options: LayoutOptions, tagName: String, articles: Seq[Article]): TypedTag[String] = layout(options) {
    div(cls := "wrapper")(
      h1(s"""Articles tagged with "$tagName""""),
      ul(
        articles.map { article =>
          li(
            a(
              href := reverseRouter.article(article),
              article.metadata.title
            )
          )
        }
      )
    )
  }
}
