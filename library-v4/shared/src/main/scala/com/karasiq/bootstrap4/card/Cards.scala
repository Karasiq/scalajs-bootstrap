package com.karasiq.bootstrap4.card

import com.karasiq.bootstrap4.context.RenderingContext
import com.karasiq.bootstrap4.icons.Icons
import com.karasiq.bootstrap4.utils.Utils

trait Cards extends CardStyles { self: RenderingContext with Icons with Utils ⇒
  import scalaTags.all._

  type Card <: AbstractCard
  val Card: CardFactory

  trait AbstractCard extends BootstrapHtmlComponent {
    def cardId: String
    def style: CardStyle
    def header: Option[Modifier]
    def footer: Option[Modifier]
    def content: Seq[Modifier]
  }

  trait CardFactory {
    def collapse(cardId: String, modifiers: Modifier*): Tag
    def title(md: Modifier*): Tag
    def subtitle(md: Modifier*): Tag
    def imageTop(md: Modifier): Tag
    def body(md: Modifier*): Tag
    def text(md: Modifier*): Tag
    def link(md: Modifier*): Tag 
    def button(icon: IconModifier, modifiers: Modifier*): Tag
    def buttons(buttons: Modifier*): Tag

    /**
      * Shortcut to PanelBuilder()
      */
    def apply(panelId: String = Bootstrap.newId, style: CardStyle = CardStyle.default,
              header: Option[Modifier] = None, footer: Option[Modifier] = None,
              content: Seq[Modifier] = Nil): Card
  }
}
