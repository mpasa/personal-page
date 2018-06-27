package controllers.articles

import java.io.File

import org.commonmark.ext.front.matter.YamlFrontMatterExtension
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import collection.JavaConverters._

import scala.io.Source

final case class Article(metadata: ArticleMetadata, html: String) {
  def url: String = s"/articles/${metadata.permalink}"
}

object Article {

  private val markdownExtensions = Seq(
    TablesExtension.create,
    YamlFrontMatterExtension.create,
    MathExtension.create
  )

  private val markdownParser = Parser.builder().extensions(markdownExtensions.asJava).build()
  private val markdownRenderer = HtmlRenderer.builder().extensions(markdownExtensions.asJava).build()

  def parse(file: File): Option[Article] = {
    val content = Source.fromFile(file).mkString
    for {
      document <- Option(markdownParser.parse(content))
      metadata <- ArticleMetadata.parse(document)
    } yield Article(metadata, markdownRenderer.render(document))
  }
}