package com.karasiq.bootstrap.collapse

import com.karasiq.bootstrap.Bootstrap
import com.karasiq.bootstrap.BootstrapAttrs._
import com.karasiq.bootstrap.BootstrapImplicits._
import org.scalajs.dom

import scalatags.JsDom.all._

class Collapse(collapseId: String = Bootstrap.newId) {
  def button(btn: ConcreteHtmlTag[dom.html.Button]): ConcreteHtmlTag[dom.html.Button] = {
    btn(`data-toggle` := "collapse", `data-target` := s"#$collapseId-collapse", aria.expanded := "false", aria.controls := s"$collapseId-collapse")
  }

  def container(md: Modifier*): Tag = {
    div("collapse".addClass, id := s"$collapseId-collapse", md)
  }
}

object Collapse {
  def apply(btnTitle: Modifier)(content: Modifier*): Modifier = {
    val c = new Collapse()
    Seq(c.button(Bootstrap.button(btnTitle)), c.container(Bootstrap.well(content)))
  }
}