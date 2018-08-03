package me.mpasa

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCode, StatusCodes}
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.StandardRoute
import scalatags.Text.TypedTag

/** Utils to complete a response with a given content
  * The response is always text/html
  */
object Ok {
  def apply(status: StatusCode, content: String): StandardRoute = {
    val response = HttpEntity(ContentTypes.`text/html(UTF-8)`, content)
    complete((status, response))
  }

  def apply(status: StatusCode, content: TypedTag[String]): StandardRoute = apply(status, "<!DOCTYPE html>" + content.render)
  def apply(content: TypedTag[String]): StandardRoute = apply(StatusCodes.OK, content)
}
