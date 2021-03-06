package me.mpasa.application.controller

import akka.http.scaladsl.server.StandardRoute
import me.mpasa.Ok
import me.mpasa.resume.{CleanTemplate, Data}
import me.mpasa.interface.ResumeT
import me.mpasa.interface.components.LayoutOptions

class Resume(resumeT: ResumeT) {

  /** Shows a page with my resume */
  def apply: StandardRoute = {
    val template = new CleanTemplate(onePage = false)
    val options = LayoutOptions("Resume", extraHead = template.styles)
    Ok(resumeT(options, Data.resume, template))
  }
}
