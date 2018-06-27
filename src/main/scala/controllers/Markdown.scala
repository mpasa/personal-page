package controllers

import controllers.articles.MathExtension
import org.commonmark.ext.front.matter.YamlFrontMatterExtension
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import collection.JavaConverters._

object Markdown {

  private val extensions = Seq(
    TablesExtension.create,
    YamlFrontMatterExtension.create,
    MathExtension.create
  )

  val parser = Parser.builder().extensions(extensions.asJava).build()
  val renderer = HtmlRenderer.builder().extensions(extensions.asJava).build()
}
