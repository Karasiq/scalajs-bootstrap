package com.karasiq.bootstrap.buttons

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.RenderingContext

trait UniversalButtonGroups { self: RenderingContext with ButtonGroups with BootstrapComponents ⇒
  import scalaTags.all._

  type ButtonGroup = UniversalButtonGroup
  object ButtonGroup extends ButtonGroupFactory {
    def apply(size: ButtonGroupSize, buttons: Modifier*): ButtonGroup = {
      new UniversalButtonGroup(size, buttons: _*)
    }
  }

  type ButtonToolbar = UniversalButtonToolbar
  object ButtonToolbar extends ButtonToolbarFactory {
    def apply(buttonGroups: ButtonGroup*): ButtonToolbar = {
      new UniversalButtonToolbar(buttonGroups: _*)
    }
  }

  class UniversalButtonGroup(val size: ButtonGroupSize, val buttons: Modifier*) extends AbstractButtonGroup {
    override def renderTag(md: ModifierT*): TagT = {
      div("btn-group".addClass, size, role := "group", aria.label := "Button group", md)(
        buttons
      )
    }
  }

  class UniversalButtonToolbar(val buttonGroups: ButtonGroup*) extends AbstractButtonToolbar {
    override def renderTag(md: ModifierT*): TagT = {
      div(`class` := "btn-toolbar", role := "toolbar", aria.label := "Button toolbar", md)(
        buttonGroups.map(_.renderTag())
      )
    }
  }
}
