package com.karasiq.bootstrap_text.form

import com.karasiq.bootstrap_text.BootstrapImplicits._

import scalatags.Text.all._


object Form {
  def submit(text: Modifier): Tag = {
    button(`type` := "submit", Seq("btn", "btn-default").map(_.addClass), text)
  }

  def apply(md: Modifier*): Tag = {
    form(md)
  }

  def inline(md: Modifier*): Tag = {
    form("form-inline".addClass, md)
  }
}
