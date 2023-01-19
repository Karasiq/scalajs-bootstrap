package com.karasiq.bootstrap.carousel

import rx.Rx

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap.icons.Icons
import com.karasiq.bootstrap.utils.Utils

trait UniversalCarousels { self: RenderingContext with Carousels with Utils with Icons with BootstrapComponents ⇒
  import scalaTags.all._

  import BootstrapAttrs._

  class UniversalCarousel(val carouselId: String, val content: Rx[Seq[Modifier]]) extends AbstractCarousel {
    protected val carouselElementId = s"$carouselId-carousel"

    def indicators: Rx[Tag] = Rx {
      def mkIndicator(index: Int): Tag = {
        li(`data-target` := s"#$carouselElementId", `data-slide-to` := index)
      }

      val indexes = content().indices
      ol(`class` := "carousel-indicators")(
        mkIndicator(indexes.head)(`class` := "active"),
        for (i ← indexes.tail) yield mkIndicator(i)
      )
    }

    def slides: Rx[Tag] = Rx {
      val data = content()
      div(`class` := "carousel-inner", role := "listbox")(
        div(`class` := "item active", data.head),
        for (slide ← data.tail) yield div(`class` := "item", slide)
      )
    }

    def carousel: Tag = {
      div(id := carouselElementId, Seq("carousel", "slide").map(_.addClass))(
        indicators,
        slides,
        a(`class` := "left carousel-control", href := s"#$carouselElementId", role := "button", `data-slide` := "prev")(
          Bootstrap.icon("chevron-left"),
          span(`class` := "sr-only", "Previous")
        ),
        a(
          `class`      := "right carousel-control",
          href         := s"#$carouselElementId",
          role         := "button",
          `data-slide` := "next"
        )(
          Bootstrap.icon("chevron-right"),
          span(`class` := "sr-only", "Next")
        )
      )
    }

    def render(md: Modifier*): Modifier = {
      carousel(`data-ride` := "carousel")(md: _*)
    }
  }

  object UniversalCarousel {
    def slide(image: String, content: Modifier*): Modifier = {
      Seq(
        img(src := image),
        div(`class` := "carousel-caption")(content: _*)
      )
    }
  }
}
