package me.mpasa.application.controllers.articles

import me.mpasa.domain.model.Article

/** An article being shown in a page, with links to the previous and next */
final case class ShownArticle(article: Article, previous: Option[Article], next: Option[Article])

