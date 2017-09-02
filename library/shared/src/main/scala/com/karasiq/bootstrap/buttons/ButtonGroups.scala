package com.karasiq.bootstrap.buttons

import com.karasiq.bootstrap.context.RenderingContext

trait ButtonGroups extends ButtonGroupStyles { self: RenderingContext with Buttons ⇒
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
