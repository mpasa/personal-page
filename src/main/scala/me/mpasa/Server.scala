package me.mpasa

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.softwaremill.macwire._
import me.mpasa.application.controller._
import me.mpasa.domain.service.{ArticleParserService, MarkdownService}
import me.mpasa.infrastructure.persistence.resources.ArticleRepositoryResources
import me.mpasa.interface._
import me.mpasa.interface.components.{FooterT, HeaderT, LayoutT, SocialT}

import scala.concurrent.ExecutionContextExecutor

trait Modules {
  // Reverse router
  lazy val reverseRouter = wire[ReverseRouter]
  // Services
  lazy val articleParserService = wire[ArticleParserService]
  lazy val markdownService = wire[MarkdownService]
  // Repositories
  lazy val articleRepository = wire[ArticleRepositoryResources]
  // Controllers
  lazy val aboutMe = wire[AboutMe]
  lazy val notFound = wire[NotFound]
  lazy val resume = wire[Resume]
  lazy val rss = wire[Rss]
  lazy val sitemap = wire[SiteMap]
  lazy val archives = wire[Archives]
  lazy val showArticle = wire[ShowArticle]
  // Interface components
  lazy val footerT = wire[FooterT]
  lazy val headerT = wire[HeaderT]
  lazy val layoutT = wire[LayoutT]
  lazy val socialT = wire[SocialT]
  // Interface
  lazy val aboutMeT = wire[AboutMeT]
  lazy val archivesT = wire[ArchivesT]
  lazy val articleT = wire[ArticleT]
  lazy val homeT = wire[HomeT]
  lazy val notFoundT = wire[NotFoundT]
  lazy val pageT = wire[PageT]
  lazy val resumeT = wire[ResumeT]
}

trait RouterModule extends Modules {
  lazy val router = new Router(this)
}

object Server extends RouterModule {

  implicit val system: ActorSystem = ActorSystem("my-system")
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  def main(args: Array[String]): Unit = {
    val host: String = "0.0.0.0"
    val port: Int = 8000
    Http().newServerAt(host, port).bind(router.routes)
    println(s"Server online at $host ($port)")
  }
}
