package me.mpasa.application.controllers

import akka.http.scaladsl.server.StandardRoute
import me.mpasa.Ok
import me.mpasa.resume.{CleanTemplate, Data}
import me.mpasa.templates.ResumeT
import me.mpasa.templates.components.LayoutOptions

class Resume(resumeT: ResumeT) {

  /** Shows an "about me" page */
  def apply: StandardRoute = {
    val template = new CleanTemplate(onePage = false)
    val options = LayoutOptions("Resume", extraHead = template.styles)
    Ok(resumeT(options, Data.resume, template))
  }
}
