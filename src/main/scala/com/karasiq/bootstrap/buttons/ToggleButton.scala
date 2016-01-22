package com.karasiq.bootstrap.buttons

import org.scalajs.dom
import rx._

import scalatags.JsDom.all._

final class ToggleButton(btn: ConcreteHtmlTag[dom.html.Button]) {
  val state: Var[Boolean] = Var(false)

  val button: dom.html.Button = {
    btn(onclick := {
      state.update(!state())
    }).render
  }

  Obs(state, "toggle-button-state-changer") {
    if (state()) {
      button.classList.remove("active")
    } else {
      button.classList.add("active")
    }
  }
}