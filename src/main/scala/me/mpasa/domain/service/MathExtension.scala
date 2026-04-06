package me.mpasa.domain.service

import org.commonmark.node.Text
import org.commonmark.parser.Parser
import org.commonmark.parser.Parser.ParserExtension
import org.commonmark.parser.delimiter.{DelimiterProcessor, DelimiterRun}

/** The delimiter processor. Capture things between $$ or $$$ */
class MathDelimiterProcessor extends DelimiterProcessor {
  override def getOpeningCharacter: Char = '$'
  override def getClosingCharacter: Char = '$'
  override def getMinLength: Int = 2
  override def process(opener: DelimiterRun, closer: DelimiterRun): Int = {
    val delimiterUse = opener.length match {
      case x if x > 3 => 0
      case _          => 2
    }

    if (delimiterUse > 0) {
      opener.getOpener.insertAfter(new Text("$" * delimiterUse))
      closer.getCloser.insertAfter(new Text("$" * delimiterUse))
    }

    delimiterUse
  }
}

/** Commonmark extension to avoid markdown rendering of KaTex expressions */
class MathExtension extends ParserExtension {
  override def extend(parserBuilder: Parser.Builder): Unit = {
    parserBuilder.customDelimiterProcessor(new MathDelimiterProcessor)
    ()
  }
}

object MathExtension {
  def create: MathExtension = new MathExtension
}
