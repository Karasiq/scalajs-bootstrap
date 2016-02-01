package com.karasiq.bootstrap.form

import com.karasiq.bootstrap.Bootstrap
import com.karasiq.bootstrap.BootstrapImplicits._

import scalatags.JsDom.all
import scalatags.JsDom.all._

object FormInputGroup {
  def createInput(tpe: String, md: Modifier*): Tag = {
    input(`type` := tpe, "form-control".addClass, id := Bootstrap.newId, md)
  }

  def label(md: Modifier*): Tag = all.label(md)

  def text(md: Modifier*): Tag = this.createInput("text", md:_*)

  def number(md: Modifier*): Tag = this.createInput("number", md:_*)

  def email(md: Modifier*): Tag = this.createInput("email", md:_*)

  def password(md: Modifier*): Tag = this.createInput("password", md:_*)

  def addon(md: Modifier*): Tag = {
    div("input-group-addon".addClass, md)
  }

  def apply(label: Modifier, md: Modifier*): Tag = {
    div("form-group".addClass, label)(
      div("input-group".addClass, md)
    )
  }
}
