package me.mpasa.application.controllers

import java.net.URLDecoder

import akka.http.scaladsl.server.StandardRoute
import me.mpasa.Ok
import me.mpasa.domain.repository.ArticleRepository
import me.mpasa.interface.ArchivesT
import me.mpasa.interface.components.LayoutOptions

/** Controllers to show different post archives
  * - All posts
  * - Tags
  * - Posts by tag
  */
class Archives(notFound: NotFound, archivesT: ArchivesT, articlesRepository: ArticleRepository) {

  /** Shows a list with all the posts */
  def all: StandardRoute = {
    val options = LayoutOptions(title = "Archives")
    Ok(archivesT.all(options, articlesRepository.all))
  }

  /** Shows a list with all the tags */
  def tags: StandardRoute = {
    val options = LayoutOptions(title = "Tags")
    Ok(archivesT.tags(options, articlesRepository.allTags))
  }

  /** Shows a list of articles for a given tag */
  def tag(name: String): StandardRoute = {
    val nameDecoded = URLDecoder.decode(name, "UTF-8")
    val tagsSet = articlesRepository.allTags.toSet
    if (tagsSet.contains(nameDecoded)) {
      val options = LayoutOptions(title = nameDecoded)
      val articles = articlesRepository.all.filter(_.metadata.tags.contains(nameDecoded))
      Ok(archivesT.tag(options, nameDecoded, articles))
    } else {
      notFound.apply
    }
  }
}
