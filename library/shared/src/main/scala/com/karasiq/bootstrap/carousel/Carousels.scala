package com.karasiq.bootstrap.carousel

import rx.{Rx, Var}

import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap.utils.Utils

trait Carousels { self: RenderingContext with Utils ⇒
  import scalaTags.all._

  type Carousel <: AbstractCarousel
  val Carousel: CarouselFactory

  trait AbstractCarousel extends BootstrapComponent {
    def carouselId: String
    def content: Rx[Seq[Modifier]]
  }

  /** A slideshow component for cycling through elements, like a carousel.
    * @note
    *   Nested carousels are not supported.
    * @see
    *   [[http://getbootstrap.com/javascript/#carousel]]
    */
  trait CarouselFactory {
    def apply(data: Rx[Seq[Modifier]], id: String = Bootstrap.newId): Carousel
    def apply(content: Modifier*): Carousel = apply(Var(content))

    def slide(image: String, content: Modifier*): Modifier
  }
}
