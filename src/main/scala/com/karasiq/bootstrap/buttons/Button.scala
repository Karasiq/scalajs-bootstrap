package com.karasiq.bootstrap.buttons

object Button {
  // Shortcut to ButtonBuilder()
  def apply(style: ButtonStyle = ButtonStyle.primary, size: ButtonSize = ButtonSize.default, block: Boolean = false, active: Boolean = false, disabled: Boolean = false): ButtonBuilder = {
    ButtonBuilder(style, size, block, active, disabled)
  }
}
