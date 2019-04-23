package me.mpasa.interface

import me.mpasa.ReverseRouter
import me.mpasa.interface.components.LayoutOptions
import scalatags.Text.TypedTag
import scalatags.Text.all._

class NotFoundT(layout: PageT, reverseRouter: ReverseRouter) {

  /** 404 template */
  def apply(options: LayoutOptions): TypedTag[String] = layout(options) {
    div(cls := "notFound wrapper")(
      h1("404"),
      p(
        "This page doesn't exist. This is bad, but not that bad! You can still find all my articles ",
        a(href := reverseRouter.archives, "here"),
        "."
      )
    )
  }
}
