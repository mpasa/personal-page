package me.mpasa.controllers.articles

import akka.http.scaladsl.server.StandardRoute
import me.mpasa.controllers.NotFound
import me.mpasa.Ok
import me.mpasa.templates.ArticleT
import me.mpasa.templates.components.LayoutOptions

import scala.util.Try

object ShowArticle {

  /** Shows an article by its permalink */
  def apply(permalink: String): StandardRoute = {
    val articlesByDate = Articles.all.sortBy(_.metadata.published.toEpochDay)
    val result = for {
      article <- Articles.byPermalink.get(permalink)
      currentIndex <- articlesByDate.zipWithIndex.find(_._1 == article).map(_._2)
    } yield {
      val previous = Try(articlesByDate(currentIndex - 1)).toOption
      val next = Try(articlesByDate(currentIndex + 1)).toOption
      val shownArticle = ShownArticle(article, previous, next)
      val options = LayoutOptions(article.metadata.title, Seq("articles"))
      Ok(ArticleT(options, shownArticle))
    }
    result.getOrElse(NotFound.apply)
  }
}
