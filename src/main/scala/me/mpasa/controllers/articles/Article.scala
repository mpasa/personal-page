package me.mpasa.controllers.articles

import me.mpasa.controllers.Markdown

/** An article is just content (HTML) and metadata */
final case class Article(metadata: ArticleMetadata, html: String) {
  def url: String = s"/articles/${metadata.permalink}"
}

object Article {

  /** Parses an article from a markdown file */
  def parse(markdown: Markdown)(content: String): Option[Article] = {
    for {
      document <- Option(markdown.parser.parse(content))
      metadata <- ArticleMetadata.parse(document)
    } yield Article(metadata, markdown.renderer.render(document))
  }
}