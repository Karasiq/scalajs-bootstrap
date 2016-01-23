package com.karasiq.bootstrap.buttons

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.{Bootstrap, BootstrapHtmlComponent}
import org.scalajs.dom
import rx._

import scalatags.JsDom.all._

final class ToggleButton(btn: ConcreteHtmlTag[dom.html.Button]) extends BootstrapHtmlComponent[dom.html.Button] {
  val state: Var[Boolean] = Var(false)

  override def renderTag(md: Modifier*): RenderedTag = {
    btn(
      "active".classIf(state),
      onclick := Bootstrap.jsClick { _ ⇒
        state.update(!state.now)
      },
      md
    )
  }
}