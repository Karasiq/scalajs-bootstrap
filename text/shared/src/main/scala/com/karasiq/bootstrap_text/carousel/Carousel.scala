package com.karasiq.bootstrap_text.carousel

import com.karasiq.bootstrap_text.BootstrapAttrs._
import com.karasiq.bootstrap_text.BootstrapImplicits._
import com.karasiq.bootstrap_text.{Bootstrap, BootstrapAttrs, BootstrapComponent}

import scalatags.Text.all._

sealed abstract class Carousel(options: Map[String, Any]) extends BootstrapComponent {
  def carouselId: String
  def content: Seq[Modifier]

  private def indicators = {
    def mkIndicator(index: Int): Tag = {
      li(`data-target` := s"#$carouselId-carousel", `data-slide-to` := index)
    }

    val indexes = content.indices
    ol(`class` := "carousel-indicators")(
      mkIndicator(indexes.head)(`class` := "active"),
      for (i <- indexes.tail) yield mkIndicator(i)
    )
  }

  private def slides = {
    val data = content
    div(`class` := "carousel-inner", role := "listbox")(
      div(`class` := "item active", data.head),
      for (slide <- data.tail) yield div(`class` := "item", slide)
    )
  }

  private def carousel: Tag = {
    div(id := s"$carouselId-carousel", Bootstrap.dataProperties(options),
      BootstrapAttrs.`data-ride` := "carousel", Seq("carousel", "slide").map(_.addClass))(
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

  /* def create(interval: Int = 5000, pause: String = "hover", wrap: Boolean = true, keyboard: Boolean = true, modifiers: Modifier = ()): Element = {
    val e = carousel(modifiers).render
    val options = js.Object().asInstanceOf[CarouselOptions]
    options.interval = interval
    options.pause = pause
    options.wrap = wrap
    options.keyboard = keyboard
    jQuery(e).carousel(options)
    e
  } */

  override def render(md: Modifier*): Modifier = {
    carousel(md:_*)
  }
}

/**
  * A slideshow component for cycling through elements, like a carousel.
  * @note Nested carousels are not supported.
  * @see [[http://getbootstrap.com/javascript/#carousel]]
  */
object Carousel {
  def apply(data: Seq[Modifier], id: String = Bootstrap.newId, options: Map[String, Any] = Map.empty): Carousel = new Carousel(options) {
    override val carouselId: String = id
    override val content: Seq[Modifier] = data
  }

  def apply(content1: Modifier, content: Modifier*): Carousel = {
    this.apply(content1 +: content)
  }

  def slide(image: String, content: Modifier*): Modifier = {
    Seq(
      img(src := image),
      div(`class` := "carousel-caption", content)
    )
  }
}
