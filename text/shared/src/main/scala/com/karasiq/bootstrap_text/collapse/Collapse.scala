package com.karasiq.bootstrap_text.collapse

import com.karasiq.bootstrap_text.Bootstrap
import com.karasiq.bootstrap_text.BootstrapAttrs._
import com.karasiq.bootstrap_text.BootstrapImplicits._

import scalatags.Text.all._

class Collapse(collapseId: String = Bootstrap.newId) {
  def button(btn: Tag): Tag = {
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