package com.karasiq.bootstrap_text.buttons

import com.karasiq.bootstrap_text.BootstrapHtmlComponent
import com.karasiq.bootstrap_text.BootstrapImplicits._

import scalatags.Text.all._

object DisabledButton {
  def apply(btn: Tag, state: Boolean): DisabledButton = {
    new DisabledButton(btn, state)
  }
}

final class DisabledButton(btn: Tag, state: Boolean) extends BootstrapHtmlComponent {
  override def renderTag(md: Modifier*): RenderedTag = {
    btn(
      "disabled".classIf(state),
      md
    )
  }
}
