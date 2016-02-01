package com.karasiq.bootstrap.form


import com.karasiq.bootstrap.BootstrapImplicits._
import org.scalajs.dom

import scalatags.JsDom.all._


object Form {
  def submit(text: Modifier): ConcreteHtmlTag[dom.html.Button] = {
    button(`type` := "submit", Seq("btn", "btn-default").map(_.addClass), text)
  }

  def apply(md: Modifier*): ConcreteHtmlTag[dom.html.Form] = {
    form(md)
  }

  def inline(md: Modifier*): ConcreteHtmlTag[dom.html.Form] = {
    form("form-inline".addClass, md)
  }
}
