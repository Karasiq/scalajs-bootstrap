package com.karasiq.bootstrap4.card

import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap4.grid.Grids
import com.karasiq.bootstrap4.icons.Icons
import com.karasiq.bootstrap4.utils.Utils

trait UniversalCards extends Cards { self: RenderingContext with Icons with Utils with Grids ⇒
  import scalaTags.all._

  import BootstrapAttrs._

  type Card = CardBuilder
  object Card extends CardFactory {
    def collapse(cardId: String, modifiers: Modifier*): Tag = {
      span(cursor.pointer, `data-toggle` := "collapse", `data-target` := s"#$cardId-card-content", modifiers)
    }

    def title(md: Modifier*): Tag = {
      h4("card-title".addClass, md)
    }

    def subtitle(md: Modifier*): Tag = {
      h6("card-subtitle".addClass, md)
    }

    def imageTop(md: Modifier): Tag = {
      img("card-img-top".addClass, md)
    }

    def body(md: Modifier*): Tag = {
      div("card-body".addClass, md)
    }

    def text(md: Modifier*): Tag = {
      p("card-text".addClass, md)
    }

    def link(md: Modifier*): Tag = {
      a("card-link".addClass, md)
    }

    def button(icon: IconModifier, modifiers: Modifier*): Tag = {
      a(href := "javascript:void(0);", icon, modifiers)
    }

    def buttons(buttons: Modifier*): Tag = {
      span(Bootstrap.pull.right, buttons)
    }

    def group(cards: Modifier*): Tag = {
      div(`class` := "card-group", cards)
    }

    def apply(
        panelId: String = Bootstrap.newId,
        header: Option[Modifier] = None,
        footer: Option[Modifier] = None,
        content: Seq[Modifier] = Nil
    ): CardBuilder = {
      CardBuilder(panelId, header, footer, content)
    }
  }

  case class CardBuilder(cardId: String, header: Option[Modifier], footer: Option[Modifier], content: Seq[Modifier])
      extends AbstractCard {
    def withId(newId: String): CardBuilder = {
      copy(cardId = newId)
    }

    def withHeader(modifiers: Modifier*): CardBuilder = {
      copy(header = Some(modifiers))
    }

    def withFooter(modifiers: Modifier*): CardBuilder = {
      copy(footer = Some(modifiers))
    }

    def withContent(content: Modifier*): CardBuilder = {
      copy(content = content)
    }

    def withBody(content: Modifier*): CardBuilder = {
      withContent(Card.body(content: _*))
    }

    def renderTag(md: Modifier*): TagT = {
      div(`class` := "card", id := cardId)(
        for (h ← header) yield div("card-header".addClass, id := s"$cardId-card-header", h),
        div(id := cardId + "-card-content", `class` := "collapse show")(content),
        for (f ← footer) yield div("card-footer".addClass, id := s"$cardId-card-footer", f),
        md
      )
    }
  }
}
