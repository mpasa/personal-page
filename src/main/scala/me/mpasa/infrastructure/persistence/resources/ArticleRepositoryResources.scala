package me.mpasa.infrastructure.persistence.resources

import me.mpasa.domain.model.{Article, ArticleRepository}
import me.mpasa.domain.service.ArticleParserService

import scala.io.Source
import scala.util.Try

class ArticleRepositoryResources(articleParserService: ArticleParserService) extends ArticleRepository {

  /** List of all the articles */
  lazy val all: Seq[Article] = {
    val resources = Source.fromResource("articles/Published.txt").getLines()
    val files = resources.map { resource =>
      Try(Source.fromResource("articles/" + resource).mkString).toOption
    }.flatten
    files.flatMap(articleParserService.parse).toSeq
  }

}
