package com.karasiq.bootstrap_text.buttons

object Button {
  /**
    * Shortcut to [[com.karasiq.bootstrap_text.buttons.ButtonBuilder ButtonBuilder]].
    */
  def apply(style: ButtonStyle = ButtonStyle.default, size: ButtonSize = ButtonSize.default, block: Boolean = false, active: Boolean = false, disabled: Boolean = false): ButtonBuilder = {
    ButtonBuilder(style, size, block, active, disabled)
  }
}
