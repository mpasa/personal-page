
package me.mpasa

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler
import controllers.{Home, SiteMap}

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
    path("sitemap.xml") {
      get {
        SiteMap.apply
      }
    },
    pathPrefix("assets") {
      getFromResourceDirectory("public")
    }
  )

  val routes = handleExceptions(handler)(routesList.reduce(_ ~ _))
}
