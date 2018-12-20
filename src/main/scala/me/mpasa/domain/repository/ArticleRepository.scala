package me.mpasa.domain.repository

import me.mpasa.domain.model.Article

trait ArticleRepository {

  /** List of all the articles */
  def all: Seq[Article]

  /** A sequence of all the tags in the articles */
  def allTags: Seq[String] = all.flatMap(_.metadata.tags).distinct

  /** A map of articles by permalink */
  def byPermalink: Map[String, Article] = all.map(article => article.metadata.permalink -> article).toMap

}
