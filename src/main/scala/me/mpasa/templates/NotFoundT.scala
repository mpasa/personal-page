package me.mpasa.templates

import me.mpasa.Router
import scalatags.Text.TypedTag
import scalatags.Text.all._
import me.mpasa.templates.components.LayoutOptions

object NotFoundT {

  /** 404 template */
  def apply(options: LayoutOptions): TypedTag[String] = PageT(options) {
    div(cls := "notFound wrapper")(
      h1("404"),
      p(
        "This page doesn't exist. This is bad, but not that bad! You can still find all my articles ",
        a(href := Router.Reverse.archives, "here"), "."
      )
    )
  }
}


