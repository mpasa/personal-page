package me.mpasa.application.controllers.articles

import akka.http.scaladsl.server.StandardRoute
import me.mpasa.Ok
import me.mpasa.application.controllers.NotFound
import me.mpasa.templates.ArticleT
import me.mpasa.templates.components.LayoutOptions

import scala.util.Try

class ShowArticle(articles: Articles, notFound: NotFound, articleT: ArticleT) {

  /** Shows an article by its permalink */
  def apply(permalink: String): StandardRoute = {
    val articlesByDate = articles.all.sortBy(_.metadata.published.toEpochDay)
    val result = for {
      article <- articles.byPermalink.get(permalink)
      currentIndex <- articlesByDate.zipWithIndex.find(_._1 == article).map(_._2)
    } yield {
      val previous = Try(articlesByDate(currentIndex - 1)).toOption
      val next = Try(articlesByDate(currentIndex + 1)).toOption
      val shownArticle = ShownArticle(article, previous, next)
      val options = LayoutOptions(article.metadata.title, description = article.metadata.description, css = Seq("articles"))
      Ok(articleT(options, shownArticle))
    }
    result.getOrElse(notFound.apply)
  }
}
