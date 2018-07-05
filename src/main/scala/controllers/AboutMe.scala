package controllers

import akka.http.scaladsl.server.StandardRoute
import me.mpasa.Ok
import org.commonmark.node.Node
import templates.AboutMeT
import templates.components.LayoutOptions

import scala.io.Source

object AboutMe {

  /** Shows an "about me" page */
  def apply: StandardRoute = {
    val content = Source.fromResource("About.md").mkString
    val document: Node = Markdown.parser.parse(content)
    val options = LayoutOptions("About me", Seq("articles"))
    Ok(AboutMeT(options, document))
  }
}
