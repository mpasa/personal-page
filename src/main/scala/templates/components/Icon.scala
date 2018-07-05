package templates.components

import scalatags.Text.all._
import scalatags.text.Frag

/** Extension of [[Frag]] to work with FontAwesome icons
  * We use a hack to introduce more classes to the icon, as doing span(cls := "a", cls := "b") results in the class
  * "ab" instead of "a b".
  *
  * @param classes extra classes for the icon
  */
case class Icon(classes: String) extends Frag {
  override def writeTo(strb: StringBuilder): Unit = strb ++= render
  override def render: String = span(cls := "icon " + classes).render
}

object Icon {
  val CALENDAR: Icon = Icon("far fa-calendar-alt")
  val CIRCLE: Icon = Icon("fas fa-circle")
}
