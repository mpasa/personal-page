package controllers

import java.time.ZoneOffset.UTC
import java.time.format.DateTimeFormatter
import java.util.Locale

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.StandardRoute
import controllers.articles.{Article, Articles}
import me.mpasa.Router
import scalatags.Text.all._
import scalatags.Text.tags2.title
import scalatags.text.Frag

object Rss {

  private val rss = tag("rss")
  private val version = attr("version")
  private val channel = tag("channel")
  private val link = tag("link")
  private val guid = tag("guid")
  private val description = tag("description")
  private val pubDate= tag("pubDate")
  private val item = tag("item")

  private case class CData(data: RawFrag) extends Frag {
    override def render: String = "<![CDATA[" + data.render + "]]>"
    override def writeTo(strb: StringBuilder): Unit = strb ++= render
  }

  private val rfc2822 = DateTimeFormatter
    .ofPattern("EEE, dd MMM yyyy HH:mm:ss Z")
    .withLocale(new Locale("en_US"))

  private def get(articles: Seq[Article]) = {
    rss(version := "2.0")(
      channel(
        title("Miguel Pérez Pasalodos' blog"),
        link(SiteMap.DOMAIN),
        articles.headOption.map(article => pubDate(article.metadata.published.atStartOfDay.toString)),
        description("A blog about Computer Science"),
        articles.map { article =>
          article.metadata.published.atStartOfDay(UTC).format(rfc2822)
          val url = SiteMap.DOMAIN + Router.Reverse.article(article)
          item(
            title(article.metadata.title),
            link(url),
            guid(url),
            pubDate(article.metadata.published.atStartOfDay(UTC).format(rfc2822)),
            description(CData(raw(article.html)))
          )
        }
      )
    )
  }

  def apply: StandardRoute = {
    val response = HttpEntity(
      ContentTypes.`text/xml(UTF-8)`,
      """<?xml version="1.0"?>""" + get(Articles.all).render
    )
    complete(response)
  }
}
