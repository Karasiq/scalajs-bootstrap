package com.karasiq.bootstrap4.carousel

import rx.Rx

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap4.icons.Icons
import com.karasiq.bootstrap4.utils.Utils

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
        div(`class` := "carousel-item active", data.head),
        for (slide ← data.tail) yield div(`class` := "carousel-item", slide)
      )
    }

    def carousel: Tag = {
      div(id := carouselElementId, Seq("carousel", "slide").map(_.addClass))(
        indicators,
        slides,
        a(`class` := "carousel-control-prev", href := s"#$carouselElementId", role := "button", `data-slide` := "prev")(
          span(`class` := "carousel-control-prev-icon", aria.hidden := true),
          span(`class` := "sr-only", "Previous")
        ),
        a(`class` := "carousel-control-next", href := s"#$carouselElementId", role := "button", `data-slide` := "next")(
          span(`class` := "carousel-control-next-icon", aria.hidden := true),
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
        img(`class` := "d-block w-100", src := image),
        div(`class` := "carousel-caption")(content: _*)
      )
    }
  }
}
