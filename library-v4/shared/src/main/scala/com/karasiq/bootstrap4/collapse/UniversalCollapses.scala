package com.karasiq.bootstrap4.collapse

import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap4.utils.Utils

trait UniversalCollapses { self: RenderingContext with Collapses with Utils ⇒
  import scalaTags.all._

  import BootstrapAttrs._

  type Collapse = UniversalCollapse
  object Collapse extends CollapseFactory {
    def create(title: Modifier, collapseId: String = Bootstrap.newId): UniversalCollapse = {
      new UniversalCollapse(title, collapseId)
    }
  }

  class UniversalCollapse(val title: Modifier, val collapseId: String = Bootstrap.newId) extends AbstractCollapse {

    protected val collapseElementId = s"$collapseId-collapse"

    def toggle: Modifier = {
      Seq(
        `data-toggle` := "collapse",
        `data-target` := s"#$collapseElementId",
        aria.expanded := false,
        aria.controls := collapseElementId
      )
    }

    def container: Tag = {
      div("collapse".addClass, id := collapseElementId)
    }

    def renderTag(md: ModifierT*): TagT = {
      div(Bootstrap.button(title, this.toggle), container(md))
    }
  }
}
