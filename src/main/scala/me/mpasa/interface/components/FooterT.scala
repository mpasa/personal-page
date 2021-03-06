package me.mpasa.interface.components

import scalatags.Text.TypedTag
import scalatags.Text.all._

class FooterT {

  /** Template of the footer */
  def apply: TypedTag[String] = footer(cls := "wrapper") {
    val projectPage = "https://github.com/mpasa/personal-page"
    span(
      "Site built using ",
      a(href := projectPage, "Scala"),
      " with ",
      span(cls := "heart fas fa-heart")
    )
  }
}
