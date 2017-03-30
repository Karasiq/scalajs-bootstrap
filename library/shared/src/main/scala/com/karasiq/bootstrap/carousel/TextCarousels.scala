package com.karasiq.bootstrap.carousel

import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap.utils.Utils
import rx.Rx

import scala.language.postfixOps

trait TextCarousels { self: Carousels with RenderingContext with Utils ⇒
  import scalaTags.all._

  /**
    * A slideshow component for cycling through elements, like a carousel.
    * @note Nested carousels are not supported.
    * @see [[http://getbootstrap.com/javascript/#carousel]]
    */
  object Carousel extends CarouselFactory {
    def apply(data: Rx[Seq[Modifier]], id: String = Bootstrap.newId): Carousel = {
      new Carousel(id, data)
    }
  }
}
