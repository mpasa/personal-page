package controllers.articles

import java.io.File

object Articles {
  lazy val all: Seq[Article] = {
    val directory = new File("/home/miguel/projects/mpasa/src/main/resources/articles")
    if (directory.exists && directory.isDirectory) {
      val files = directory.listFiles.filter(_.getName.endsWith(".md"))
      files.flatMap(Article.parse)
    } else {
      Seq.empty
    }
  }

  lazy val byPermalink = all.map(article => article.metadata.permalink -> article).toMap
}
