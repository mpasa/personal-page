package me.mpasa.controllers

import java.time.ZoneOffset.UTC
import java.time.format.DateTimeFormatter

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.StandardRoute
import me.mpasa.ReverseRouter
import me.mpasa.controllers.articles.{Article, Articles}
import scalatags.Text.all._
import scalatags.Text.tags2.title
import scalatags.text.Frag

class Rss(siteMap: SiteMap, reverseRouter: ReverseRouter, articles: Articles) {

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

  private def get(articles: Seq[Article]) = {
    rss(version := "2.0")(
      channel(
        title("Miguel Pérez Pasalodos' blog"),
        link(siteMap.DOMAIN),
        articles.headOption.map(article => pubDate(article.metadata.published.atStartOfDay(UTC).format(rfc2822))),
        description("A blog about Computer Science"),
        articles.map { article =>
          val url = siteMap.DOMAIN + reverseRouter.article(article)
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
      """<?xml version="1.0"?>""" + get(articles.all).render
    )
    complete(response)
  }
}
