package com.karasiq.bootstrap_text.buttons

import com.karasiq.bootstrap_text.BootstrapHtmlComponent
import com.karasiq.bootstrap_text.BootstrapImplicits._

import scalatags.Text.all._

object ToggleButton {
  def apply(btn: Tag, state: Boolean): ToggleButton = {
    new ToggleButton(btn, state)
  }
}

final class ToggleButton(btn: Tag, state: Boolean) extends BootstrapHtmlComponent {
  override def renderTag(md: Modifier*): RenderedTag = {
    btn(
      "active".classIf(state),
      md
    )
  }
}