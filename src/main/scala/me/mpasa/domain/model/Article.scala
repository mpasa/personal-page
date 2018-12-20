package me.mpasa.domain.model

/** An article is just content (HTML) and metadata */
final case class Article(metadata: ArticleMetadata, html: String) {
  def url: String = s"/articles/${metadata.permalink}"
}
