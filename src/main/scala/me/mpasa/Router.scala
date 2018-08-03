package me.mpasa

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler
import me.mpasa.controllers.articles.{Archives, Article, ShowArticle}
import me.mpasa.controllers.{AboutMe, Resume, Rss, SiteMap}
import me.mpasa.templates.HomeT

object Router {

  object Reverse {
    // About me
    val about = "/about-me"
    val resume = "/resume"
    // Articles
    def article(permalink: String): String = s"/articles/$permalink"
    def article(entity: Article): String = article(entity.metadata.permalink)
    // Article archives
    val archives = "/archives"
    val tags = "/archives/tags"
    def tag(name: String): String = s"/archives/tags/$name"
    // Feed
    val feed = "/feed.xml"
  }

  private val handler = ExceptionHandler { case e =>
    throw e;
    //complete(HttpResponse(InternalServerError, entity = "Internal Error"))
  }

  private val routesList = List(
    // Homepage
    path("")(get(Ok(HomeT.apply))),
    // Articles
    path("feed.xml")(get(Rss.apply)),
    path("archives")(get(Archives.all)),
    path("archives" / "tags")(get(Archives.tags)),
    path("archives" / "tags" / Remaining) { tag => get(Archives.tag(tag)) },
    path("articles" / Remaining) { permalink => get(ShowArticle(permalink)) },
    // About me
    path("about-me")(get(AboutMe.apply)),
    path("resume")(get(Resume.apply)),
    // Sitemap
    path("sitemap.xml")(get(SiteMap.apply)),
    // Assets
    pathPrefix("assets")(getFromResourceDirectory("public"))
  )

  val routes = handleExceptions(handler)(routesList.reduce(_ ~ _))
}