package controllers

import scalatags.Text.TypedTag
import scalatags.Text.all._
import templates.{LayoutOptions, PageT}

import scala.io.Source

object AboutMe {
  def apply: TypedTag[String] = {
    val content = Source.fromResource("about.md").mkString
    val document = Markdown.parser.parse(content)
    val options = LayoutOptions("About me | Miguel Pérez Pasalodos", Seq("articles"))
    val html = div(cls := "article")(
      h1("About me"),
      raw(Markdown.renderer.render(document))
    )
    PageT(options)(html)
  }
}
