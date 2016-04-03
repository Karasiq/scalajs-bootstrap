package com.karasiq.bootstrap.card

import com.karasiq.bootstrap.BootstrapHtmlComponent
import com.karasiq.bootstrap.BootstrapImplicits._
import org.scalajs.dom

import scalatags.JsDom.all._

case class CardBuilder(cardId: String, style: CardStyle, inverse: Boolean, header: Option[Modifier], footer: Option[Modifier]) extends BootstrapHtmlComponent[dom.html.Div] {
  def withId(newId: String): CardBuilder = {
    copy(cardId = newId)
  }

  def withStyle(style: CardStyle): CardBuilder = {
    copy(style = style)
  }

  def withHeader(modifiers: Modifier*): CardBuilder = {
    copy(header = Some(modifiers))
  }

  def withFooter(modifiers: Modifier*): CardBuilder = {
    copy(footer = Some(modifiers))
  }

  def withInverse(value: Boolean): CardBuilder = {
    copy(inverse = value)
  }

  def renderTag(content: Modifier*): RenderedTag = {
    div((Seq("card") ++ style.styleClass).map(_.addClass), if (inverse) "card-inverse".addClass else (), id := cardId)(
      for (h <- header) yield div("card-header".addClass, id := s"$cardId-card-header", h),
      div("card-block".addClass, "collapse".addClass, "in".addClass, id := s"$cardId-card-body", content),
      for (f <- footer) yield div("card-footer".addClass, id := s"$cardId-card-footer", f)
    )
  }
}
