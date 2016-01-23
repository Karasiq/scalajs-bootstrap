package com.karasiq.bootstrap.buttons

import org.scalajs.dom
import org.scalajs.dom.Element

import scalatags.JsDom.all._

trait ButtonWrapper extends Modifier {
  val button: dom.html.Button

  override def applyTo(t: Element): Unit = {
    this.button.applyTo(t)
  }
}
