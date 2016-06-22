package com.karasiq.bootstrap.buttons

object Button {
  /**
    * Shortcut to [[com.karasiq.bootstrap.buttons.ButtonBuilder ButtonBuilder]].
    */
  def apply(style: ButtonStyle = ButtonStyle.default, size: ButtonSize = ButtonSize.default, block: Boolean = false, active: Boolean = false, disabled: Boolean = false): ButtonBuilder = {
    ButtonBuilder(style, size, block, active, disabled)
  }
}
