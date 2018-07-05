package controllers.articles

import java.time.LocalDate

import org.commonmark.ext.front.matter.YamlFrontMatterVisitor
import org.commonmark.node.Node

import scala.collection.JavaConverters._
import scala.util.Try

/** Metadata for an article
  *
  * @param title title of the article, shown above the content
  * @param permalink the text used to build the
  * @param published the date the article was originally published
  * @param tags a list of strings describing the content with keywords
  */
final case class ArticleMetadata(title: String, permalink: String, published: LocalDate, tags: Set[String])

object ArticleMetadata {

  /** Extracts a map of metadata from a document using YAML Front matter */
  private def metadataMap(document: Node): Map[String, Seq[String]] = {
    val visitor = new YamlFrontMatterVisitor
    document.accept(visitor)
    visitor.getData.asScala.toMap.map { case (k, v) => k -> v.asScala }
  }

  /** Parses metadata from a markdown node */
  def parse(document: Node): Option[ArticleMetadata] = {
    val map = metadataMap(document)
    for {
      title <- map.get("title").flatMap(_.headOption)
      permalink <- map.get("permalink").flatMap(_.headOption)
      published <- map.get("published").flatMap(_.headOption).flatMap(datetime => Try(LocalDate.parse(datetime)).toOption)
      tags <- map.get("tags")
    } yield ArticleMetadata(title, permalink, published, tags.toSet)
  }
}
