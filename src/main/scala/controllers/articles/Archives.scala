package controllers.articles

import akka.http.scaladsl.server.StandardRoute
import controllers.NotFound
import me.mpasa.Ok
import templates.ArchivesT
import templates.components.LayoutOptions

/** Controllers to show different post archives
  * - All posts
  * - Tags
  * - Posts by tag
  */
object Archives {

  /** Shows a list with all the posts */
  def all: StandardRoute = {
    val options = LayoutOptions(title = "Archives")
    Ok(ArchivesT.all(options, Articles.all))
  }

  /** Shows a list with all the tags */
  def tags: StandardRoute = {
    val options = LayoutOptions(title = "Tags")
    Ok(ArchivesT.tags(options, Articles.allTags))
  }

  /** Shows a list of articles for a given tag */
  def tag(name: String): StandardRoute = {
    val tagsSet = Articles.allTags.toSet
    if (tagsSet.contains(name)) {
      val options = LayoutOptions(title = name)
      val articles = Articles.all.filter(_.metadata.tags.contains(name))
      Ok(ArchivesT.tag(options, name, articles))
    } else {
      NotFound.apply
    }
  }
}
