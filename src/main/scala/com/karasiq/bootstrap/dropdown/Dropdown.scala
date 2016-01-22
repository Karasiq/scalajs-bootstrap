package com.karasiq.bootstrap.dropdown

import com.karasiq.bootstrap.buttons.ButtonBuilder

import scalatags.JsDom.all._

class Dropdown(title: String, dropdownId: String, items: Tag*) {
  def dropdown: Tag = {
    val `data-toggle` = "data-toggle".attr

    div(`class` := "dropdown")(
      ButtonBuilder(classes = Seq("dropdown-toggle")).build(id := dropdownId, `data-toggle` := "dropdown", aria.haspopup := true, aria.expanded := false)(
        title,
        span(`class` := "caret")
      ),
      ul(`class` := "dropdown-menu", aria.labelledby := dropdownId)(
        items
      )
    )
  }

  def dropup: Tag = this.dropdown(`class` := "dropup")
}

object Dropdown {
  def item(title: String, target: String): Tag = {
    li(a(href := target, title))
  }

  def apply(title: String, dropdownId: String, items: Tag*): Tag = new Dropdown(title, dropdownId, items:_*).dropdown
}