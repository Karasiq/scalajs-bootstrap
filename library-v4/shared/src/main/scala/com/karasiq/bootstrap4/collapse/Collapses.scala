package com.karasiq.bootstrap4.collapse

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap4.utils.Utils

trait Collapses { self: RenderingContext with Utils with BootstrapComponents ⇒
  import scalaTags.all._

  type Collapse <: AbstractCollapse
  val Collapse: CollapseFactory

  trait AbstractCollapse extends BootstrapHtmlComponent {
    def collapseId: String
    def toggle: Modifier
    def container: Tag
  }

  trait CollapseFactory {
    def create(title: Modifier, collapseId: String = Bootstrap.newId): Collapse

    def apply(title: Modifier)(content: Modifier*): Tag = {
      create(title, Bootstrap.newId).renderTag(content: _*)
    }
  }
}
