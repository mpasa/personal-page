
package me.mpasa

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler
import controllers.articles.ShowArticle
import controllers.{AboutMe, Home, SiteMap}

object Router {

  private val handler = ExceptionHandler { case e =>
    throw e;
    //complete(HttpResponse(InternalServerError, entity = "Internal Error"))
  }

  private val routesList = List(
    path("") {
      get {
        Ok(Home.apply)
      }
    },
    path("articles" / Remaining) { permalink =>
      get {
        ShowArticle(permalink)
      }
    },
    path("sitemap.xml") {
      get {
        SiteMap.apply
      }
    },
    path("about-me-test") {
      get {
        Ok(AboutMe.apply)
      }
    },
    pathPrefix("assets") {
      getFromResourceDirectory("public")
    }
  )

  val routes = handleExceptions(handler)(routesList.reduce(_ ~ _))
}
