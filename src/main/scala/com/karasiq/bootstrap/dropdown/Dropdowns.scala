package com.karasiq.bootstrap.dropdown

import com.karasiq.bootstrap.context.BootstrapBundle

trait Dropdowns { self: BootstrapBundle ⇒
  import BootstrapAttrs._

  import scalaTags.all._

  private[dropdown] final class Dropdown(title: Modifier, dropdownId: String, items: Modifier){
    def dropdown: Tag = {
      div(`class` := "dropdown")(
        Bootstrap.button("dropdown-toggle".addClass, id := dropdownId, `data-toggle` := "dropdown", aria.haspopup := true, aria.expanded := false)(
          title,
          raw("&nbsp"),
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
    def item(md: Modifier*): Tag = {
      this.link("javascript:void(0);", md:_*)
    }

    def link(target: String, md: Modifier*): Tag = {
      li(a(href := target, md))
    }

    def apply(title: Modifier, items: Modifier*): Tag = {
      new Dropdown(title, Bootstrap.newId, items).dropdown
    }

    def dropup(title: Modifier, items: Modifier*): Tag = {
      new Dropdown(title, Bootstrap.newId, items).dropup
    }
  }
}
