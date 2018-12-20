package me.mpasa

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.softwaremill.macwire._
import me.mpasa.controllers._
import me.mpasa.controllers.articles.{Archives, Articles, ShowArticle}
import me.mpasa.templates._
import me.mpasa.templates.components.{FooterT, HeaderT, LayoutT, SocialT}

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Success, Try}

trait Modules {
  lazy val router = wire[Router]
  lazy val reverseRouter = wire[ReverseRouter]

  // Controllers
  lazy val aboutMe = wire[AboutMe]
  lazy val markdown = wire[Markdown]
  lazy val notFound = wire[NotFound]
  lazy val resume = wire[Resume]
  lazy val rss = wire[Rss]
  lazy val sitemap = wire[SiteMap]

  // Articles
  lazy val archives = wire[Archives]
  lazy val articles = wire[Articles]
  lazy val showArticle = wire[ShowArticle]

  // Templates
  lazy val aboutMeT = wire[AboutMeT]
  lazy val archivesT = wire[ArchivesT]
  lazy val articleT = wire[ArticleT]
  lazy val homeT = wire[HomeT]
  lazy val notFoundT = wire[NotFoundT]
  lazy val pageT = wire[PageT]
  lazy val resumeT = wire[ResumeT]

  // Template components
  lazy val footerT = wire[FooterT]
  lazy val headerT = wire[HeaderT]
  lazy val layoutT = wire[LayoutT]
  lazy val socialT = wire[SocialT]
}

object Server extends Modules {

  implicit val system: ActorSystem = ActorSystem("my-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  def main(args: Array[String]): Unit = {



    Try(args(0).toInt) match {
      case Success(port) =>
        val host: String = "localhost"
        Http().bindAndHandle(router.routes, host, port)
        println(s"Server online at $host ($port)")
      case _ =>
        println("You must specify the port as the first argument of the app")
    }
  }
}
