package me.mpasa.domain.model

/** An article being shown in a page, with links to the previous and next */
final case class ShownArticle(article: Article, previous: Option[Article], next: Option[Article])
