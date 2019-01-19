package me.mpasa.application.controller

import akka.http.scaladsl.server.StandardRoute
import me.mpasa.Ok
import me.mpasa.domain.model.{ArticleRepository, ShownArticle}
import me.mpasa.interface.ArticleT
import me.mpasa.interface.components.LayoutOptions
import scalatags.Text.all._

import scala.util.Try

class ShowArticle(articleRepository: ArticleRepository, notFound: NotFound, articleT: ArticleT) {

  /** Shows an article by its permalink */
  def apply(permalink: String): StandardRoute = {
    val articlesByDate = articleRepository.all.sortBy(_.metadata.published.toEpochDay)
    val result = for {
      article <- articleRepository.byPermalink.get(permalink)
      currentIndex <- articlesByDate.zipWithIndex.find(_._1 == article).map(_._2)
    } yield {
      val previous = Try(articlesByDate(currentIndex - 1)).toOption
      val next = Try(articlesByDate(currentIndex + 1)).toOption
      val shownArticle = ShownArticle(article, previous, next)
      val options = LayoutOptions(
        title = article.metadata.title, 
        description = article.metadata.description, 
        css = Seq("articles"),
        extraHead = Seq(script(src := "/assets/scripts/article.js"))
      )
      Ok(articleT(options, shownArticle))
    }
    result.getOrElse(notFound())
  }
}
