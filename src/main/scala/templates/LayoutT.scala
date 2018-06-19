package templates

import scalatags.Text.all._
import scalatags.Text.{TypedTag, tags2}

/** Base layout for all pages */
object LayoutT {
  def apply(options: LayoutOptions)(content: TypedTag[String]*): TypedTag[String] = html(
    head(
      meta(charset := "UTF-8"),
      link(rel := "stylesheet", href := "/assets/styles/style.css"),
      link(rel := "stylesheet", href := "https://fonts.googleapis.com/css?family=Open+Sans"),
      link(rel := "stylesheet", href := "https://use.fontawesome.com/releases/v5.0.13/css/all.css"),
      tags2.title(options.title)
    ),
    body(content)
  )
}
