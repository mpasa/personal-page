package me.mpasa.interface

import java.time.format.{DateTimeFormatterBuilder, TextStyle}
import java.time.temporal.ChronoField

import me.mpasa.ReverseRouter
import me.mpasa.application.controller.SiteMap
import me.mpasa.domain.model.{Article, ShownArticle}
import me.mpasa.interface.components.{Icon, LayoutOptions}
import scalatags.Text.TypedTag
import scalatags.Text.all._

/** Templates to display articles */
class ArticleT(layout: PageT, reverseRouter: ReverseRouter, siteMap: SiteMap) {

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
      article.metadata.published.format(DATE_FORMATTER)
    ),
    div(cls := "tags")(
      article.metadata.tags.toSeq.map { tag =>
        span(cls := "tag")(a(href := reverseRouter.tag(tag), tag))
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

  /** Shows a section with links to the previous and next articles */
  private def prevNext(previous: Option[Article], next: Option[Article]) = {
    div(cls := "prevNext")(
      div(cls := "previous")(
        previous.map { previous =>
          a(href := reverseRouter.article(previous), "« " + previous.metadata.title)
        }
      ),
      div(cls := "next")(
        next.map { next =>
          a(href := reverseRouter.article(next), next.metadata.title + " »")
        }
      )
    )
  }

  /** Shows a comments section using the Disqus API */
  private def disqusComments(article: Article) = raw(
    s"""
      |<div id="disqus_thread"></div>
      |<script>
      |var disqus_config = function () {
      |this.page.url = "${siteMap.DOMAIN}${reverseRouter.article(article)}";
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
  def apply(options: LayoutOptions, article: ShownArticle): TypedTag[String] = layout(options) {
    div(cls := "wrapper article")(
      h1(article.article.metadata.title),
      div(cls := "flex spaceBetween")(
        showMetadata(article.article),
        div(cls := "sharing")(
          twitterShareButton(article.article.metadata.title),
          twitterFollowButton
        )
      ),
      raw(article.article.html),
      prevNext(article.previous, article.next),
      disqusComments(article.article)
    )
  }
}
