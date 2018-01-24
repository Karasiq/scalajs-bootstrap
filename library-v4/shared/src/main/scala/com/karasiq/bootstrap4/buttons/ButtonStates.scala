package com.karasiq.bootstrap4.buttons

import rx.Var

import com.karasiq.bootstrap.context.RenderingContext

trait ButtonStates { self: RenderingContext with Buttons ⇒
  import scalaTags.all._

  type ToggleButton <: AbstractStatefulButton
  val ToggleButton: StatefulButtonFactory

  type DisabledButton <: AbstractStatefulButton
  val DisabledButton: StatefulButtonFactory

  trait AbstractStatefulButton extends BootstrapHtmlComponent {
    def state: Var[Boolean]
  }

  trait StatefulButtonFactory {
    def apply(button: Tag): AbstractStatefulButton
  }
}
