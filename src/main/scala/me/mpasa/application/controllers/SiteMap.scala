package me.mpasa.application.controllers

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.StandardRoute
import me.mpasa.ReverseRouter
import me.mpasa.domain.repository.ArticleRepository
import scalatags.Text
import scalatags.Text.all._

/** Generates a sitemap with all the pages */
class SiteMap(reverseRouter: ReverseRouter, articleRepository: ArticleRepository) {

  val DOMAIN = "https://mpasa.me"

  // URL set
  val urlset = tag("urlset")
  val xmlns = attr("xmlns")
  val xmlnsXsi = attr("xmlns:xsi")
  val xsiSchemaLocation = attr("xsi:schemaLocation")
  // URLs
  val url = tag("url")
  val loc = tag("loc")

  // Sequence of all URLs relative to the domain
  private val urls: Seq[String] = {
    Seq("") ++                                    // Homepage
    Seq(reverseRouter.about) ++                  // About me
    Seq(reverseRouter.resume) ++                 // Resume
    // Blog
    articleRepository.all.map(reverseRouter.article) ++   // Articles
    Seq(reverseRouter.archives) ++               // Archives
    Seq(reverseRouter.tags) ++                   // List of tags
    articleRepository.allTags.map(reverseRouter.tag) ++   // Tags
    Seq(reverseRouter.feed)                      // Feed
  }

  /** Returns a sitemap given a list of all the urls */
  private def get(urls: Seq[String]): Text.TypedTag[String] = {
    urlset(
      xmlns := "http://www.sitemaps.org/schemas/sitemap/0.9",
      xmlnsXsi := "http://www.w3.org/2001/XMLSchema-instance",
      xsiSchemaLocation := "http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd"
    )(
      urls.map(urlS => url(loc(DOMAIN + urlS)))
    )
  }

  /** Gets the sitemap as a XML response */
  def apply: StandardRoute = {
    val response = HttpEntity(
      ContentTypes.`text/xml(UTF-8)`,
      """<?xml version="1.0"?>""" + get(urls).render
    )
    complete(response)
  }
}
