package me.mpasa.interface.components

import scalatags.Text.all._
import scalatags.Text.{TypedTag, tags2}

/** Base layout for all pages */
class LayoutT {

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

  /** A cookie consent for the EU
    * It uses https://cookieconsent.insites.com
    */
  private val cookiesConsent = Seq(
    link(rel := "stylesheet", href := "//cdnjs.cloudflare.com/ajax/libs/cookieconsent2/3.0.3/cookieconsent.min.css"),
    script(src := "//cdnjs.cloudflare.com/ajax/libs/cookieconsent2/3.0.3/cookieconsent.min.js"),
    script(raw(
      """
        |window.addEventListener("load", function(){
        |  window.cookieconsent.initialise({
        |    "palette": {
        |      "popup": {
        |        "background": "#000"
        |      },
        |      "button": {
        |        "background": "transparent",
        |        "text": "#14a7d0",
        |        "border": "#14a7d0"
        |      }
        |    }
        |  })
        |});
      """.stripMargin))
  )

  def apply(options: LayoutOptions)(pageContent: TypedTag[String]*): TypedTag[String] = html(
    head(
      meta(charset := "UTF-8"),
      options.css.map { css =>
        link(rel := "stylesheet", href := s"/assets/styles/$css.css")
      },
      link(rel := "stylesheet", href := "https://fonts.googleapis.com/css?family=Noto+Sans|Raleway"),
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
      // Title
      tags2.title(options.fullTitle),
      // Description
      options.description.map(description => meta(name := "description", content := description)),
      options.extraHead,
      analytics,
      cookiesConsent
    ),
    body(
      pageContent,
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
