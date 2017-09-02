package com.karasiq.bootstrap.collapse

import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap.utils.Utils

trait Collapses { self: RenderingContext with Utils⇒
  import scalaTags.all._

  type Collapse <: AbstractCollapse
  val Collapse: CollapseFactory

  trait AbstractCollapse extends BootstrapHtmlComponent {
    def collapseId: String
    def toggle: Modifier
    def container: Tag
  }

  trait CollapseFactory {
    def apply(title: Modifier, collapseId: String = Bootstrap.newId)(content: Modifier*): Collapse
  }
}