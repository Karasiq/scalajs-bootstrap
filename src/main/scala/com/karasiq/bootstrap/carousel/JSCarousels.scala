package com.karasiq.bootstrap.carousel

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.JSRenderingContext
import com.karasiq.bootstrap.utils.Utils
import org.scalajs.jquery.jQuery
import rx.Rx

import scala.language.postfixOps
import scala.scalajs.js

trait JSCarousels { self: JSRenderingContext with Carousels with BootstrapComponents with Utils ⇒
  import scalaTags.all._

  class JSCarousel(carouselId: String, content: Rx[Seq[Modifier]]) extends Carousel(carouselId, content) {
    def create(interval: Int = 5000, pause: String = "hover", wrap: Boolean = true, keyboard: Boolean = true, modifiers: Modifier = ()): Element = {
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

  /**
    * A slideshow component for cycling through elements, like a carousel.
    * @note Nested carousels are not supported.
    * @see [[http://getbootstrap.com/javascript/#carousel]]
    */
  object Carousel extends CarouselFactory {
    def apply(data: Rx[Seq[Modifier]], id: String = Bootstrap.newId): Carousel = {
      new JSCarousel(id, data)
    }
  }
}
