package com.karasiq.bootstrap4.test.frontend

import com.karasiq.bootstrap.components.generic.GenComponent
import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap4.carousel.Carousels

object TestCarousel {
  type RC = RenderingContext with Carousels

  def apply(imgSrc: String)(implicit rc: RenderingContext with Carousels): TestCarousel = {
    new TestCarousel(imgSrc)
  }
}

// Renders on ScalaJS and JVM
final class TestCarousel(imgSrc: String)(implicit val rc: TestCarousel.RC) extends GenComponent {
  type RC = TestCarousel.RC
  import rc._
  import scalaTags.all._
  
  def component = Carousel(
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
  )
}
