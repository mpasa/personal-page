package me.mpasa.controllers.articles

import scala.io.Source
import scala.util.Try

object Articles {

  /** List of all the articles */
  lazy val all: Seq[Article] = {
    val resources = Source.fromResource("articles/Published.txt").getLines
    val files = resources.map { resource =>
      Try(Source.fromResource("articles/" + resource).mkString).toOption
    }.flatten
    files.flatMap(Article.parse).toSeq
  }

  /** A sequence of all the tags in the articles */
  lazy val allTags = all.flatMap(_.metadata.tags).distinct

  /** A map of articles by permalink */
  lazy val byPermalink = all.map(article => article.metadata.permalink -> article).toMap
}
