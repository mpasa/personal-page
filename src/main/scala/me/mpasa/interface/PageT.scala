package me.mpasa.interface

import me.mpasa.interface.components.{FooterT, HeaderT, LayoutOptions, LayoutT}
import scalatags.Text.TypedTag
import scalatags.Text.all._

/** All pages with header */
class PageT(layout: LayoutT, headerT: HeaderT, footerT: FooterT) {
  def apply(options: LayoutOptions)(content: TypedTag[String]*): TypedTag[String] = {
    val optionsWithCommonCSS = options.copy(css = options.css :+ "common")
    layout(optionsWithCommonCSS)(
      div(cls := "main")(
        headerT.apply,
        div(cls := "contentWrapper")(content),
        footerT.apply
      )
    )
  }
}
