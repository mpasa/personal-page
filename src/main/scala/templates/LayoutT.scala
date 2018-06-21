package templates

import scalatags.Text.all._
import scalatags.Text.{TypedTag, tags2}

/** Base layout for all pages */
object LayoutT {

  /** Analytics tracking code */
  private val analytics = raw(
    """
    <script async src="https://www.googletagmanager.com/gtag/js?id=UA-121170824-1"></script>
    <script>
      window.dataLayer = window.dataLayer || [];
      function gtag(){dataLayer.push(arguments);}
      gtag('js', new Date());
      gtag('config', 'UA-121170824-1');
    </script>
    """
  )

  def apply(options: LayoutOptions)(content: TypedTag[String]*): TypedTag[String] = html(
    head(
      meta(charset := "UTF-8"),
      link(rel := "stylesheet", href := "/assets/styles/style.css"),
      link(rel := "stylesheet", href := "https://fonts.googleapis.com/css?family=Open+Sans"),
      link(rel := "stylesheet", href := "https://use.fontawesome.com/releases/v5.0.13/css/all.css"),
      tags2.title(options.title),
      analytics
    ),
    body(content)
  )
}
