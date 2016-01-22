package com.karasiq.bootstrap.buttons

import org.scalajs.dom
import rx._

import scalatags.JsDom.all._

final class DisabledButton(btn: ConcreteHtmlTag[dom.html.Button]) {
  val state: Var[Boolean] = Var(false)

  val button: dom.html.Button = {
    btn(onclick := {
      if (!state()) {
        // Disables button
        state.update(true)
      }
    }).render
  }

  Obs(state, "disabled-button-state-changer") {
    if (state()) {
      button.classList.add("disabled")
    } else {
      button.classList.remove("disabled")
    }
  }
}
