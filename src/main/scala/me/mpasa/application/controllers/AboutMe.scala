package me.mpasa.application.controllers

import akka.http.scaladsl.server.StandardRoute
import me.mpasa.Ok
import me.mpasa.application.service.MarkdownService
import me.mpasa.interface.AboutMeT
import me.mpasa.interface.components.LayoutOptions
import org.commonmark.node.Node

import scala.io.Source

class AboutMe(markdown: MarkdownService, aboutMeT: AboutMeT) {

  /** Shows an "about me" page */
  def apply: StandardRoute = {
    val content = Source.fromResource("About.md").mkString
    val document: Node = markdown.parser.parse(content)
    val options = LayoutOptions("About me", css = Seq("articles"))
    Ok(aboutMeT(options, document))
  }
}
