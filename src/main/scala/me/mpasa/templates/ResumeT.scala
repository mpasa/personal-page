package me.mpasa.templates

import me.mpasa.resume.model.{Resume, Template}
import me.mpasa.templates.components.LayoutOptions
import scalatags.Text.TypedTag
import scalatags.Text.all._

object ResumeT {

  /** Template for the resume page */
  def apply(options: LayoutOptions, resume: Resume, template: Template): TypedTag[String] = PageT(options)(
    div(cls := "wrapper article")(
      h1("Resume"),
      template.render(resume)
    )
  )
}
