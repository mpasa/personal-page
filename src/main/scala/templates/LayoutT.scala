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
      options.css.map { css =>
        link(rel := "stylesheet", href := s"/assets/styles/${css}.css")
      },
      link(rel := "stylesheet", href := "https://fonts.googleapis.com/css?family=Open+Sans"),
      link(rel := "stylesheet", href := "https://use.fontawesome.com/releases/v5.0.13/css/all.css"),
      // KaTex CDN
      link(rel := "stylesheet", href := "https://cdn.jsdelivr.net/npm/katex@0.10.0-beta/dist/katex.min.css"),
      script(src := "https://cdn.jsdelivr.net/npm/katex@0.10.0-beta/dist/katex.min.js"),
      script(src := "https://cdn.jsdelivr.net/npm/katex@0.10.0-beta/dist/contrib/auto-render.min.js"),
      // Highlight.js CDN
      link(rel := "stylesheet", href := "//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/styles/tomorrow.min.css"),
      script(src := "//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/highlight.min.js"),
      script(src := "//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/languages/scala.min.js"),
      // Font Awesome
      link(rel := "stylesheet", href := "https://use.fontawesome.com/releases/v5.1.0/css/all.css"),
      tags2.title(options.title),
      analytics
    ),
    body(
      content,
      script(raw(
        """
          |hljs.initHighlightingOnLoad();
          |renderMathInElement(document.body, { delimiters: [
          | { left: "$$$", right: "$$$", display: true},
          | { left: "$$", right: "$$", display: false},
          |]});
        """.stripMargin
      ))
    )
  )
}
