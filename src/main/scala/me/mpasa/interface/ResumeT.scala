package me.mpasa.interface

import me.mpasa.resume.model.{Resume, Template}
import me.mpasa.interface.components.LayoutOptions
import scalatags.Text.TypedTag
import scalatags.Text.all._

class ResumeT(layout: PageT) {

  /** Template for the resume page */
  def apply(options: LayoutOptions, resume: Resume, template: Template): TypedTag[String] = layout(options)(
    div(cls := "wrapper article")(
      h1("Resume"),
      template.render(resume)
    )
  )
}
