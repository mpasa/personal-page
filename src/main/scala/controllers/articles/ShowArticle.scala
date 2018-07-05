package controllers.articles

import akka.http.scaladsl.server.StandardRoute
import controllers.NotFound
import me.mpasa.Ok
import templates.ArticleT
import templates.components.LayoutOptions

object ShowArticle {

  /** Shows an article by its permalink */
  def apply(permalink: String): StandardRoute = {
    Articles.byPermalink.get(permalink) match {
      case Some(article) =>
        val options = LayoutOptions(article.metadata.title, Seq("articles"))
        Ok(ArticleT(options, article))
      case None => NotFound.apply
    }
  }
}
