package controllers

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.StandardRoute
import controllers.articles.Articles
import me.mpasa.Router
import scalatags.Text
import scalatags.Text.all._

/** Generates a sitemap with all the pages */
object SiteMap {

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
    Seq(Router.Reverse.about) ++                  // About me
    // Blog
    Articles.all.map(Router.Reverse.article) ++   // Articles
    Seq(Router.Reverse.archives) ++               // Archives
    Seq(Router.Reverse.tags) ++                   // List of tags
    Articles.allTags.map(Router.Reverse.tag)      // Tags
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
    val response = HttpEntity(ContentTypes.`text/xml(UTF-8)`, get(urls).render)
    complete(response)
  }
}
