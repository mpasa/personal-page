package controllers.articles

import java.time.format.{DateTimeFormatterBuilder, TextStyle}
import java.time.temporal.ChronoField

import akka.http.scaladsl.server.StandardRoute
import me.mpasa.Ok
import scalatags.Text.all._
import templates.{Icon, LayoutOptions, PageT}

object ShowArticle {

  private val DATE_FORMATTER = new DateTimeFormatterBuilder()
    .appendText(ChronoField.MONTH_OF_YEAR, TextStyle.FULL)
    .appendLiteral(" ")
    .appendValue(ChronoField.DAY_OF_MONTH)
    .appendLiteral(", ")
    .appendValue(ChronoField.YEAR)
    .toFormatter

  private def showMetadata(article: Article) = div(cls := "metadata")(
    span(cls := "publishedDate")(
      Icon.CALENDAR,
      article.metadata.published.format(DATE_FORMATTER)
    )
  )

  def apply(permalink: String): StandardRoute = {
    Articles.byPermalink.get(permalink) match {
      case Some(article) =>
        val options = LayoutOptions(article.metadata.title, Seq("articles"))
        val html = div(cls := "article")(
          h1(article.metadata.title),
          showMetadata(article),
          raw(article.html)
        )
        Ok(PageT(options)(html))
      case None => Ok(":(")
    }
  }
}
