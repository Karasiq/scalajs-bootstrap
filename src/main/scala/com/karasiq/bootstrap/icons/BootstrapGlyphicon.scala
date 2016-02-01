package com.karasiq.bootstrap.icons

import com.karasiq.bootstrap.BootstrapHtmlComponent
import org.scalajs.dom

import scalatags.JsDom.all._

final class BootstrapGlyphicon(name: String) extends BootstrapHtmlComponent[dom.html.Span] with IconModifier {
  override def renderTag(md: Modifier*): RenderedTag = {
    span(`class` := s"glyphicon glyphicon-$name", aria.hidden := true, md)
  }
}

object BootstrapGlyphicon {
  def apply(name: String): BootstrapGlyphicon = {
    new BootstrapGlyphicon(name)
  }
}