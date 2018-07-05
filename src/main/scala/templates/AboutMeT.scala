package templates

import controllers.Markdown
import org.commonmark.node.Node
import scalatags.Text.TypedTag
import scalatags.Text.all._
import templates.components.LayoutOptions

object AboutMeT {

  /** Template for the About me page */
  def apply(options: LayoutOptions, document: Node): TypedTag[String] = PageT(options)(
    div(cls := "wrapper article")(
      h1("About me"),
      raw(Markdown.renderer.render(document))
    )
  )
}
