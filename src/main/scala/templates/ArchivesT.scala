package templates

import controllers.articles.Article
import me.mpasa.Router
import scalatags.Text.TypedTag
import scalatags.Text.all._
import templates.components.LayoutOptions

/** Templates to show different post archives */
object ArchivesT {

  /** Shows a list of all the published posts */
  def all(options: LayoutOptions, articles: Seq[Article]): TypedTag[String] = PageT(options) {
    div(cls := "wrapper")(
      h1("Archives"),
      p(
        "Here you can find all the articles I've written, sorted by date in descending order. ",
        "You can also check out a list of them grouped by ", a(href := Router.Reverse.tags, "tag"), "."
      ),
      ul(
        articles.sortBy(_.metadata.published)(Ordering.by(-_.toEpochDay)).map { article =>
          li(
            a(
              href := Router.Reverse.article(article),
              article.metadata.title
            )
          )
        }
      )
    )
  }

  /** Shows a list of all the tags */
  def tags(options: LayoutOptions, tags: Seq[String]): TypedTag[String] = PageT(options) {
    div(cls := "wrapper")(
      h1("Tags"),
      p(
        "Here you can find all the tags I've written about, sorted alphanumerically in ascending order. ",
        "You can also check out a list of ", a(href := Router.Reverse.archives, "all the posts"), "."
      ),
      ul(
        tags.sorted.map { tag =>
          li(
            a(
              href := Router.Reverse.tag(tag),
              tag
            )
          )
        }
      )
    )
  }

  /** Shows a list of all the tags */
  def tag(options: LayoutOptions, tagName: String, articles: Seq[Article]): TypedTag[String] = PageT(options) {
    div(cls := "wrapper")(
      h1(s"""Articles tagged with "$tagName""""),
      ul(
        articles.map { article =>
          li(
            a(
              href := Router.Reverse.article(article),
              article.metadata.title
            )
          )
        }
      )
    )
  }
}
