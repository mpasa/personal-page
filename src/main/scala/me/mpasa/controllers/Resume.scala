package me.mpasa.controllers

import akka.http.scaladsl.server.StandardRoute
import me.mpasa.Ok
import me.mpasa.resume.{CleanTemplate, Data}
import me.mpasa.templates.ResumeT
import me.mpasa.templates.components.LayoutOptions

object Resume {

  /** Shows an "about me" page */
  def apply: StandardRoute = {
    val template = CleanTemplate
    val options = LayoutOptions("Resume", extraHead = template.styles)
    Ok(ResumeT(options, Data.resume, template))
  }
}
