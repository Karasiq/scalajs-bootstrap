package com.karasiq.bootstrap_text.icons

import com.karasiq.bootstrap_text.BootstrapHtmlComponent

import scalatags.Text.all._

final class BootstrapGlyphicon(name: String) extends BootstrapHtmlComponent with IconModifier {
  override def renderTag(md: Modifier*): RenderedTag = {
    span(`class` := s"glyphicon glyphicon-$name", aria.hidden := true, md)
  }
}

object BootstrapGlyphicon {
  def apply(name: String): BootstrapGlyphicon = {
    new BootstrapGlyphicon(name)
  }
}