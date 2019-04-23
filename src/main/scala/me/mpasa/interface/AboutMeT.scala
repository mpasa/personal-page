package me.mpasa.interface

import me.mpasa.domain.service.MarkdownService
import me.mpasa.interface.components.LayoutOptions
import org.commonmark.node.Node
import scalatags.Text.TypedTag
import scalatags.Text.all._

class AboutMeT(layout: PageT, markdown: MarkdownService) {

  /** Template for the About me page */
  def apply(options: LayoutOptions, document: Node): TypedTag[String] = layout(options)(
    div(cls := "wrapper article")(
      h1("About me"),
      raw(markdown.renderer.render(document))
    )
  )
}
