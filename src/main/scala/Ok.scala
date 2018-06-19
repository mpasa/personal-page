
package me.mpasa

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.StandardRoute
import scalatags.Text.TypedTag

/** Utils to complete a response with a given content
  * The response is always text/html
  */
object Ok {
  def apply(content: String): StandardRoute = {
    val response = HttpEntity(ContentTypes.`text/html(UTF-8)`, content)
    complete(response)
  }

  def apply(content: TypedTag[String]): StandardRoute = apply("<!DOCTYPE html>" + content.render)
}
