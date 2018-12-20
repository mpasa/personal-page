package me.mpasa.application.controllers

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.StandardRoute
import me.mpasa.Ok
import me.mpasa.interface.NotFoundT
import me.mpasa.interface.components.LayoutOptions

/** Shows a generic 404 page */
class NotFound(notFoundT: NotFoundT) {
  def apply: StandardRoute = {
    val options = LayoutOptions("Page not found")
    Ok(StatusCodes.NotFound, notFoundT(options))
  }
}
