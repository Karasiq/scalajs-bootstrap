package com.karasiq.bootstrap.carousel

import com.karasiq.bootstrap.Bootstrap
import com.karasiq.bootstrap.BootstrapImplicits._
import org.scalajs.dom.Element
import org.scalajs.jquery.jQuery
import rx._

import scala.scalajs.js
import scalatags.JsDom.all
import scalatags.JsDom.all._

/**
  * @see [[https://getbootstrap.com/javascript/#carousel]]
  */
sealed trait Carousel extends Modifier {
  def carouselId: String

  def content: Rx[Seq[Modifier]]

  private val `data-target` = "data-target".attr

  private def indicators = Rx {
    val `data-slide-to` = "data-slide-to".attr

    def mkIndicator(index: Int): Tag = {
      li(`data-target` := s"#$carouselId-carousel", `data-slide-to` := index)
    }

    val indexes = content().indices
    ol(`class` := "carousel-indicators")(
      mkIndicator(indexes.head)(`class` := "active"),
      for (i <- indexes.tail) yield mkIndicator(i)
    )
  }

  private def slides = Rx {
    val data = content()
    div(`class` := "carousel-inner", role := "listbox")(
      div(`class` := "item active", data.head),
      for (slide <- data.tail) yield div(`class` := "item", slide)
    )
  }

  private def carousel: Tag = {
    val `data-ride` = "data-ride".attr
    val `data-slide` = "data-slide".attr
    div(id := s"$carouselId-carousel", `class` := "carousel slide"/*, `data-ride` := "carousel"*/)(
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

  def render(interval: Int = 5000, pause: String = "hover", wrap: Boolean = true, keyboard: Boolean = true): Element = {
    val e = carousel.render
    jQuery(e).carousel(js.Dynamic.literal(interval = interval, pause = pause, wrap = wrap, keyboard = keyboard))
    e
  }

  override def applyTo(t: Element): Unit = {
    this.render().applyTo(t)
  }
}

object Carousel {
  def reactive(data: Rx[Seq[Modifier]], id: String = Bootstrap.newId): Carousel = new Carousel {
    override val carouselId: String = id

    override val content: Rx[Seq[all.Modifier]] = data
  }

  def apply(content: Modifier*): Carousel = {
    this.reactive(Rx(content))
  }

  def slide(image: String, content: Modifier*): Modifier = {
    Seq(
      img(src := image),
      div(`class` := "carousel-caption", content)
    )
  }
}
