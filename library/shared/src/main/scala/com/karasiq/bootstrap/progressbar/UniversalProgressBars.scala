package com.karasiq.bootstrap.progressbar

import rx.Rx

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.{ReactiveImplicits, RenderingContext}

trait UniversalProgressBars { self: RenderingContext with BootstrapComponents with ReactiveImplicits with ProgressBars ⇒
  import scalaTags.all._

  type ProgressBar = UniversalProgressBar
  object ProgressBar extends ProgressBarFactory {
    def basic(value: Rx[Int]): ProgressBar = new UniversalProgressBar {
      override val progress: Rx[Int] = value

      override protected val content: Modifier = {
        span("sr-only".addClass, Rx(s"${progress()}% Complete"))
      }
    }

    def withLabel(value: Rx[Int]): ProgressBar = new UniversalProgressBar {
      override val progress: Rx[Int] = value

      override protected val content: Modifier = {
        Rx[Frag](s"${progress()}%")
      }
    }
  }

  trait UniversalProgressBar extends AbstractProgressBar with BootstrapHtmlComponent {
    protected def content: Modifier
    def progress: Rx[Int]

    override def renderTag(md: ModifierT*): TagT = {
      div("progress".addClass)(
        div(
          "progress-bar".addClass,
          role := "progressbar",
          Rx(aria.valuenow := progress()).auto,
          aria.valuemin := 0,
          aria.valuemax := 100,
          Rx[Modifier](width := progress().pct).auto,
          content,
          md
        )
      )
    }
  }
}
