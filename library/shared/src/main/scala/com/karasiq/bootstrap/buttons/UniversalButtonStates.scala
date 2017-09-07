package com.karasiq.bootstrap.buttons

import rx.Var

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.RenderingContext

trait UniversalButtonStates { self: RenderingContext with ButtonStates with BootstrapComponents ⇒
  import scalaTags.all._

  type ToggleButton = UniversalToggleButton
  object ToggleButton extends StatefulButtonFactory {
    def apply(btn: Tag): ToggleButton = {
      new ToggleButton(btn)
    }
  }

  type DisabledButton = UniversalDisabledButton
  object DisabledButton extends StatefulButtonFactory {
    def apply(btn: Tag): DisabledButton = {
      new DisabledButton(btn)
    }
  }

  class UniversalToggleButton(btn: Tag) extends AbstractStatefulButton {
    val state: Var[Boolean] = Var(false)

    override def renderTag(md: ModifierT*): TagT = {
      btn(
        "active".classIf(state),
        onclick := Callback.onClick { _ ⇒
          state.update(!state.now)
        },
        md
      )
    }
  }

  class UniversalDisabledButton(btn: Tag) extends AbstractStatefulButton {
    val state: Var[Boolean] = Var(false)

    override def renderTag(md: ModifierT*): TagT = {
      btn(
        "disabled".classIf(state),
        onclick := Callback.onClick { _ ⇒
          if (!state.now) state.update(true)
        },
        md
      )
    }
  }
}
