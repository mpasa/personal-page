package me.mpasa.templates

import scalatags.Text.TypedTag
import scalatags.Text.all._
import me.mpasa.templates.components.{FooterT, HeaderT, LayoutOptions, LayoutT}

/** All pages with header */
object PageT {
  def apply(options: LayoutOptions)(content: TypedTag[String]*): TypedTag[String] = {
    val optionsWithCommonCSS = options.copy(css = options.css :+ "common")
    LayoutT(optionsWithCommonCSS)(
      div(cls := "main")(
        HeaderT.apply,
        div(cls := "contentWrapper")(content),
        FooterT.apply
      )
    )
  }
}

