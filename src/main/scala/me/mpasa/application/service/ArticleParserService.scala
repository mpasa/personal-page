package me.mpasa.application.service

import me.mpasa.application.controllers.articles.ArticleMetadata
import me.mpasa.domain.model.Article

class ArticleParserService(markdownService: MarkdownService) {

  /** Parses an article from a markdown file */
  def parse(content: String): Option[Article] = {
    for {
      document <- Option(markdownService.parser.parse(content))
      metadata <- ArticleMetadata.parse(document)
    } yield Article(metadata, markdownService.renderer.render(document))
  }
}
