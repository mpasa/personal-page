package templates

import scalatags.Text.all._
import scalatags.text.Frag

case class Icon(classes: String) extends Frag {
  override def writeTo(strb: StringBuilder): Unit = strb ++= render
  override def render: String = span(cls := "icon " + classes).render
}

object Icon {
  val CALENDAR: Icon = Icon("far fa-calendar-alt")
  val CIRCLE: Icon = Icon("fas fa-circle")
}
