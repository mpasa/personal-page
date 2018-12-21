package me.mpasa

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler

class Router(modules: Modules) {

  private val handler = ExceptionHandler {
    case e =>
      println(e.getMessage)
      complete(HttpResponse(StatusCodes.InternalServerError, entity = "Internal Error"))
  }

  private val routesList = List(
    // Homepage
    path("")(get(Ok(modules.homeT.apply))),
    // Articles
    path("feed.xml")(get(modules.rss.apply)),
    path("archives")(get(modules.archives.all)),
    path("archives" / "tags")(get(modules.archives.tags)),
    path("archives" / "tags" / Remaining) { tag => get(modules.archives.tag(tag)) },
    path("articles" / Remaining) { permalink => get(modules.showArticle(permalink)) },
    // About me
    path("about-me")(get(modules.aboutMe.apply)),
    path("resume")(get(modules.resume.apply)),
    // Sitemap
    path("sitemap.xml")(get(modules.sitemap.apply)),
    // Assets
    pathPrefix("assets")(getFromResourceDirectory("public"))
  )

  val routes = handleExceptions(handler)(routesList.reduce(_ ~ _))
}
