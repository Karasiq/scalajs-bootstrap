package com.karasiq.bootstrap.carousel

import com.karasiq.bootstrap.context.BootstrapBundle
import rx.Rx

trait Carousels { self: BootstrapBundle ⇒
  import BootstrapAttrs._

  import scalaTags.all._

  class Carousel(carouselId: String, content: Rx[Seq[Modifier]]) extends BootstrapComponent {
    def indicators = Rx {
      def mkIndicator(index: Int): Tag = {
        li(`data-target` := s"#$carouselId-carousel", `data-slide-to` := index)
      }

      val indexes = content().indices
      ol(`class` := "carousel-indicators")(
        mkIndicator(indexes.head)(`class` := "active"),
        for (i <- indexes.tail) yield mkIndicator(i)
      )
    }

    def slides = Rx {
      val data = content()
      div(`class` := "carousel-inner", role := "listbox")(
        div(`class` := "item active", data.head),
        for (slide <- data.tail) yield div(`class` := "item", slide)
      )
    }

    def carousel: Tag = {
      div(id := s"$carouselId-carousel", Seq("carousel", "slide").map(_.addClass))(
        indicators,
        slides,
        a(`class` := "left carousel-control", href := s"#$carouselId-carousel", role := "button", `data-slide` := "prev")(
          Bootstrap.icon("chevron-left"),
          span(`class` := "sr-only", "Previous")
        ),
        a(`class` := "right carousel-control", href := s"#$carouselId-carousel", role := "button", `data-slide` := "next")(
          Bootstrap.icon("chevron-right"),
          span(`class` := "sr-only", "Next")
        )
      )
    }

    def render(md: Modifier*): Modifier = {
      carousel(`data-ride` := "carousel", md)
    }
  }

  /**
    * A slideshow component for cycling through elements, like a carousel.
    * @note Nested carousels are not supported.
    * @see [[http://getbootstrap.com/javascript/#carousel]]
    */
  trait CarouselFactory {
    def apply(data: Rx[Seq[Modifier]], id: String = Bootstrap.newId): Carousel
    def apply(content: Modifier*): Carousel = apply(Rx(content))

    def slide(image: String, content: Modifier*): Modifier = {
      Seq(
        img(src := image),
        div(`class` := "carousel-caption", content)
      )
    }
  }

  val Carousel: CarouselFactory
}
