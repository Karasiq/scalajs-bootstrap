package com.karasiq.bootstrap.buttons

import com.karasiq.bootstrap.Bootstrap
import org.scalajs.dom
import rx._

import scalatags.JsDom.all._

final class ToggleButton(btn: ConcreteHtmlTag[dom.html.Button]) extends ButtonWrapper {
  val state: Var[Boolean] = Var(false)

  val button: dom.html.Button = {
    btn(onclick := Bootstrap.jsClick { _ ⇒
      state.update(!state())
    }).render
  }

  private val obs = Obs(state, "toggle-button-state-changer") {
    if (state()) {
      button.classList.add("active")
    } else {
      button.classList.remove("active")
    }
  }

  def destroy(): Unit = {
    obs.kill()
  }
}