package controllers

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.StandardRoute
import me.mpasa.Ok
import templates.NotFoundT
import templates.components.LayoutOptions

/** Shows a generic 404 page */
object NotFound {
  def apply: StandardRoute = {
    val options = LayoutOptions("Page not found")
    Ok(StatusCodes.NotFound, NotFoundT(options))
  }
}
