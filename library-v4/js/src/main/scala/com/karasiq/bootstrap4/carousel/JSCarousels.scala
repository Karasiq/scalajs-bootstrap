package com.karasiq.bootstrap4.carousel

import scala.language.postfixOps
import scala.scalajs.js

import rx.Rx

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.JSRenderingContext
import com.karasiq.bootstrap.jquery.BootstrapJQueryContext
import com.karasiq.bootstrap4.icons.Icons
import com.karasiq.bootstrap4.utils.Utils

trait JSCarousels extends UniversalCarousels {
  self: JSRenderingContext with Carousels with Utils with Icons with BootstrapComponents with BootstrapJQueryContext ⇒
  import scalaTags.all._

  type Carousel = JSCarousel
  object Carousel extends CarouselFactory {
    def apply(data: Rx[Seq[Modifier]], id: String = Bootstrap.newId): JSCarousel = {
      new JSCarousel(id, data)
    }

    def slide(image: String, content: Modifier*): Modifier = {
      UniversalCarousel.slide(image, content)
    }
  }

  class JSCarousel(carouselId: String, content: Rx[Seq[Modifier]]) extends UniversalCarousel(carouselId, content) {
    def create(
        interval: Int = 5000,
        pause: String = "hover",
        wrap: Boolean = true,
        keyboard: Boolean = true,
        modifiers: Modifier = Bootstrap.noModifier
    ): Element = {
      val element = carousel(modifiers).render
      val options = js.Object().asInstanceOf[JSCarouselOptions]
      options.interval = interval
      options.pause = pause
      options.wrap = wrap
      options.keyboard = keyboard
      jQuery(element).carousel(options)
      element
    }

    override def render(md: Modifier*): Modifier = {
      create(modifiers = md)
    }
  }
}
