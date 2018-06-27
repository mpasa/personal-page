package controllers.articles

import java.io.File

object Articles {
  lazy val all: Seq[Article] = {
    val directory = new File("/home/miguel/projects/mpasa/src/main/resources/articles")
    val files = directory.listFiles()
    val markdowns = files.filter(_.getName.endsWith(".md"))
    markdowns.flatMap(Article.parse).toSeq
  }

  lazy val byPermalink = all.map(article => article.metadata.permalink -> article).toMap
}
