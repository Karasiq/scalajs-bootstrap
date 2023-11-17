package com.karasiq.bootstrap.carousel

import scala.language.postfixOps

import rx.Rx

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap.icons.Icons
import com.karasiq.bootstrap.utils.Utils

trait TextCarousels extends UniversalCarousels {
  self: RenderingContext with Carousels with Utils with Icons with BootstrapComponents ⇒
  import scalaTags.all._

  type Carousel = UniversalCarousel
  object Carousel extends CarouselFactory {
    def apply(data: Rx[Seq[Modifier]], id: String = Bootstrap.newId): UniversalCarousel = {
      new UniversalCarousel(id, data)
    }

    def slide(image: String, content: Modifier*): Modifier = {
      UniversalCarousel.slide(image, content)
    }
  }
}
