package me.mpasa

import me.mpasa.domain.model.Article

class ReverseRouter {
  // About me
  val about = "/about-me"
  val resume = "/resume"
  // Articles
  def article(permalink: String): String = s"/articles/$permalink"
  def article(entity: Article): String = article(entity.metadata.permalink)
  // Article archives
  val archives = "/archives"
  val tags = "/archives/tags"
  def tag(name: String): String = s"/archives/tags/$name"
  // Feed
  val feed = "/feed.xml"
}
