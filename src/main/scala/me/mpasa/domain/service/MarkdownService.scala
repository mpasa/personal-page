package me.mpasa.domain.service

import org.commonmark.ext.front.matter.YamlFrontMatterExtension
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer

import scala.jdk.CollectionConverters._

/** Utils for markdown rendering and rendering */
class MarkdownService {

  private val extensions = Seq(
    TablesExtension.create,
    YamlFrontMatterExtension.create,
    MathExtension.create
  )

  val parser = Parser.builder().extensions(extensions.asJava).build()
  val renderer = HtmlRenderer.builder().extensions(extensions.asJava).build()
}
