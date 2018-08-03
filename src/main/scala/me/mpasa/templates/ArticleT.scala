package me.mpasa.templates

import java.time.format.{DateTimeFormatterBuilder, TextStyle}
import java.time.temporal.ChronoField

import me.mpasa.controllers.SiteMap
import me.mpasa.controllers.articles.Article
import me.mpasa.Router
import scalatags.Text.TypedTag
import scalatags.Text.all._
import me.mpasa.templates.components.{Icon, LayoutOptions}

/** Templates to display articles */
object ArticleT {

  private val DATE_FORMATTER = new DateTimeFormatterBuilder()
    .appendText(ChronoField.MONTH_OF_YEAR, TextStyle.FULL)
    .appendLiteral(" ")
    .appendValue(ChronoField.DAY_OF_MONTH)
    .appendLiteral(", ")
    .appendValue(ChronoField.YEAR)
    .toFormatter

  /** Renders the metadata of an article */
  private def showMetadata(article: Article) = div(cls := "metadata")(
    span(cls := "publishedDate")(
      Icon.CALENDAR,
      article.metadata.published.format(DATE_FORMATTER),
    ),
    div(cls := "tags")(
      article.metadata.tags.toSeq.map { tag =>
        span(cls := "tag")(a(href := Router.Reverse.tag(tag), tag))
      }
    )
  )

  /** A button to share the page on Twitter */
  private def twitterShareButton(text: String) = raw(
    s"""
      |<a href="https://twitter.com/share?ref_src=twsrc%5Etfw" class="twitter-share-button" data-text="$text" data-show-count="false">
      |Tweet
      |</a>
      |<script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>
    """.stripMargin
  )

  /** A button to follow me on Twitter */
  private val twitterFollowButton = raw(
    """
      |<a href="https://twitter.com/Kamugo?ref_src=twsrc%5Etfw" class="twitter-follow-button" data-show-count="false">
      |Follow @Kamugo
      |</a>
      |<script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>
    """.stripMargin
  )

  /** Shows a comments section using the Disqus API */
  private def disqusComments(article: Article) = raw(
    s"""
      |<div id="disqus_thread"></div>
      |<script>
      |var disqus_config = function () {
      |this.page.url = "${SiteMap.DOMAIN}${Router.Reverse.article(article)}";
      |this.page.identifier = "articles/${article.metadata.permalink}";
      |};
      |
      |(function() {
      |var d = document, s = d.createElement('script');
      |s.src = 'https://mpasa.disqus.com/embed.js';
      |s.setAttribute('data-timestamp', +new Date());
      |(d.head || d.body).appendChild(s);
      |})();
      |</script>
      |<noscript>Please enable JavaScript to view the <a href="https://disqus.com/?ref_noscript">comments powered by Disqus.</a></noscript>
    """.stripMargin
  )

  /** Renders an article */
  def apply(options: LayoutOptions, article: Article): TypedTag[String] = PageT(options) {
    div(cls := "wrapper article")(
      h1(article.metadata.title),
      div(cls := "flex spaceBetween")(
        showMetadata(article),
        div(cls := "sharing")(
          twitterShareButton(article.metadata.title),
          twitterFollowButton
        )
      ),
      raw(article.html),
      disqusComments(article)
    )
  }
}
