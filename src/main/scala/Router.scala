
package me.mpasa

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler
import controllers.articles.{Archives, Article, ShowArticle}
import controllers.{AboutMe, SiteMap}
import templates.HomeT

object Router {

  object Reverse {
    // About me
    val about = "/about-me"
    // Articles
    def article(permalink: String): String = s"/articles/$permalink"
    def article(entity: Article): String = article(entity.metadata.permalink)
    // Article archives
    val archives = "/archives"
    val tags = "/archives/tags"
    def tag(name: String): String = s"/archives/tags/$name"
  }

  private val handler = ExceptionHandler { case e =>
    throw e;
    //complete(HttpResponse(InternalServerError, entity = "Internal Error"))
  }

  private val routesList = List(
    // Homepage
    path("")(get(Ok(HomeT.apply))),
    // Articles
    path("archives")(get(Archives.all)),
    path("archives" / "tags")(get(Archives.tags)),
    path("archives" / "tags" / Remaining) { tag => get(Archives.tag(tag)) },
    path("articles" / Remaining) { permalink => get(ShowArticle(permalink)) },
    // About me
    path("about-me")(get(AboutMe.apply)),
    // Sitemap
    path("sitemap.xml")(get(SiteMap.apply)),
    // Assets
    pathPrefix("assets")(getFromResourceDirectory("public"))
  )

  val routes = handleExceptions(handler)(routesList.reduce(_ ~ _))
}
