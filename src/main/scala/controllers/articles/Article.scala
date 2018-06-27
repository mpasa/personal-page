package controllers.articles

import java.io.File

import controllers.Markdown

import scala.io.Source

final case class Article(metadata: ArticleMetadata, html: String) {
  def url: String = s"/articles/${metadata.permalink}"
}

object Article {
  def parse(file: File): Option[Article] = {
    val content = Source.fromFile(file).mkString
    for {
      document <- Option(Markdown.parser.parse(content))
      metadata <- ArticleMetadata.parse(document)
    } yield Article(metadata, Markdown.renderer.render(document))
  }
}