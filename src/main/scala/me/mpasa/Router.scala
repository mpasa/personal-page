package me.mpasa

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler
import me.mpasa.controllers.articles.{Archives, ShowArticle}
import me.mpasa.controllers.{AboutMe, Resume, Rss, SiteMap}
import me.mpasa.templates.HomeT

class Router(homeT: HomeT, rss: Rss, archives: Archives, showArticle: ShowArticle, aboutMe: AboutMe, siteMap: SiteMap, resume: Resume) {

  private val handler = ExceptionHandler {
    case e =>
      println(e.getMessage)
      complete(HttpResponse(StatusCodes.InternalServerError, entity = "Internal Error"))
  }

  private val routesList = List(
    // Homepage
    path("")(get(Ok(homeT.apply))),
    // Articles
    path("feed.xml")(get(rss.apply)),
    path("archives")(get(archives.all)),
    path("archives" / "tags")(get(archives.tags)),
    path("archives" / "tags" / Remaining) { tag => get(archives.tag(tag)) },
    path("articles" / Remaining) { permalink => get(showArticle(permalink)) },
    // About me
    path("about-me")(get(aboutMe.apply)),
    path("resume")(get(resume.apply)),
    // Sitemap
    path("sitemap.xml")(get(siteMap.apply)),
    // Assets
    pathPrefix("assets")(getFromResourceDirectory("public"))
  )

  val routes = handleExceptions(handler)(routesList.reduce(_ ~ _))
}
