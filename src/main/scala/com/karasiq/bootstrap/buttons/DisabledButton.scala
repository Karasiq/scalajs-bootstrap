package com.karasiq.bootstrap.buttons

import com.karasiq.bootstrap.Bootstrap
import org.scalajs.dom
import rx._

import scalatags.JsDom.all._

final class DisabledButton(btn: ConcreteHtmlTag[dom.html.Button]) extends ButtonWrapper {
  val state: Var[Boolean] = Var(false)

  val button: dom.html.Button = btn(
    onclick := Bootstrap.jsClick { _ ⇒
      if (!state()) {
        // Disables button
        state.update(true)
      }
    }
  ).render

  private val obs = Obs(state, "disabled-button-state-changer") {
    if (state()) {
      button.classList.add("disabled")
    } else {
      button.classList.remove("disabled")
    }
  }

  def destroy(): Unit = {
    obs.kill()
  }
}
