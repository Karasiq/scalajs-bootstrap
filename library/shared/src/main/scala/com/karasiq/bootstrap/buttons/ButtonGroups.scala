package com.karasiq.bootstrap.buttons

import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap.utils.Utils

trait ButtonGroups extends ButtonGroupStyles { self: RenderingContext with Buttons with Utils ⇒
  import scalaTags.all._

  type ButtonGroup <: AbstractButtonGroup
  val ButtonGroup: ButtonGroupFactory

  type ButtonToolbar <: AbstractButtonToolbar
  val ButtonToolbar: ButtonToolbarFactory

  trait AbstractButtonGroup extends BootstrapHtmlComponent {
    def size: ButtonGroupSize
    def buttons: Seq[Modifier]
  }

  trait ButtonGroupFactory {
    def apply(size: ButtonGroupSize, buttons: Modifier*): ButtonGroup
  }

  trait AbstractButtonToolbar extends BootstrapHtmlComponent {
    def buttonGroups: Seq[ButtonGroup]
  }

  trait ButtonToolbarFactory {
    def apply(buttonGroups: ButtonGroup*): ButtonToolbar
  }
}
