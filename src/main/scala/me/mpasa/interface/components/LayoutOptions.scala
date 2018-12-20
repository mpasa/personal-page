package me.mpasa.interface.components

import scalatags.Text.TypedTag

/** Options for the rendering of the base layout
  *
  * @param title the title tag for the page
  * @param description an optional short description for the page
  * @param css list of CSS files to include
  * @param extraHead optional extra elements for the page head section
  */
final case class LayoutOptions(title: String,
                               description: Option[String] = None,
                               css: Seq[String] = Seq.empty,
                               extraHead: Seq[TypedTag[String]] = Seq.empty) {

  lazy val fullTitle = s"$title | Miguel Pérez Pasalodos"
}
