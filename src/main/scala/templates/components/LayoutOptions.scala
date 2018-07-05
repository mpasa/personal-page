package templates.components

/** Options for the rendering of the base layout
  *
  * @param title the title tag for the page
  * @param css list of CSS files to include
  */
final case class LayoutOptions(title: String, css: Seq[String] = Seq.empty) {
  lazy val fullTitle = s"$title | Miguel Pérez Pasalodos"
}
