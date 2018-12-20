package me.mpasa.controllers

import akka.http.scaladsl.server.StandardRoute
import me.mpasa.Ok
import me.mpasa.templates.AboutMeT
import me.mpasa.templates.components.LayoutOptions
import org.commonmark.node.Node

import scala.io.Source

class AboutMe(markdown: Markdown, aboutMeT: AboutMeT) {

  /** Shows an "about me" page */
  def apply: StandardRoute = {
    val content = Source.fromResource("About.md").mkString
    val document: Node = markdown.parser.parse(content)
    val options = LayoutOptions("About me", css = Seq("articles"))
    Ok(aboutMeT(options, document))
  }
}
