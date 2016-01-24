package com.karasiq.bootstrap.test.frontend

import com.karasiq.bootstrap.BootstrapComponent
import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.carousel.Carousel

import scalatags.JsDom.all._

final class TestCarousel(imgSrc: String) extends BootstrapComponent {
  override def render(md: Modifier*): Modifier = {
    Carousel(
      Carousel.slide(
        imgSrc,
        h3("First slide label"),
        p("Nulla vitae elit libero, a pharetra augue mollis interdum.")
      ),
      Carousel.slide(
        imgSrc,
        h3("Second slide label"),
        p("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
      )
    ).render(md)
  }
}
