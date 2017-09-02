package com.karasiq.bootstrap.dropdown

import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap.utils.Utils

trait UniversalDropdowns { self: RenderingContext with Dropdowns with Utils ⇒
  import scalaTags.all._

  import BootstrapAttrs._

  type Dropdown = AbstractDropdown
  object Dropdown extends DropdownFactory {
    def link(target: String, md: Modifier*): Tag = {
      li(a(href := target, md))
    }

    def apply(title: Modifier, items: Modifier*): Dropdown = {
      new UniversalDropdown(title, items)
    }
  }

  private[dropdown] final class UniversalDropdown(val title: Modifier,
                                                  val items: Seq[Modifier],
                                                  val dropdownId: String = Bootstrap.newId)
    extends AbstractDropdown {

    def dropdown: Tag = {
      div(`class` := "dropdown")(
        Bootstrap.button(
          "dropdown-toggle".addClass,
          id := dropdownId,
          `data-toggle` := "dropdown",
          aria.haspopup := true,
          aria.expanded := false,
          title,
          Bootstrap.nbsp,
          span(`class` := "caret")
        ),
        ul(`class` := "dropdown-menu", aria.labelledby := dropdownId)(items:_*)
      )
    }

    def dropup: Tag = this.dropdown(`class` := "dropup")
  }
}
