package com.karasiq.bootstrap.progressbar

import com.karasiq.bootstrap.context.BootstrapBundle
import rx.Rx

import scala.language.postfixOps

trait ProgressBars { self: BootstrapBundle ⇒
  import scalaTags.all._

  trait ProgressBar extends BootstrapHtmlComponent {
    protected def content: Modifier
    def progress: Rx[Int]

    override def renderTag(md: Modifier*): Tag = {
      div("progress".addClass)(
        div("progress-bar".addClass, role := "progressbar", Rx(aria.valuenow := progress()).auto, 
          aria.valuemin := 0, aria.valuemax := 100, Rx[Modifier](width := progress().pct).auto, content, md)
      )
    }
  }

  /**
    * Provide up-to-date feedback on the progress of a workflow or action with simple yet flexible progress bars.
    * @see [[http://getbootstrap.com/components/#progress]]
    */
  object ProgressBar {
    def basic(value: Rx[Int]): ProgressBar = new ProgressBar {
      override val progress: Rx[Int] = value

      override protected val content: Modifier = Rx {
        span("sr-only".addClass, s"${progress()}% Complete")
      }
    }

    def withLabel(value: Rx[Int]): ProgressBar = new ProgressBar {
      override val progress: Rx[Int] = value

      override protected val content: Modifier = Rx {
        s"${progress()}%"
      }
    }
  }

  sealed trait ProgressBarStyle extends ModifierFactory {
    val className: String
    lazy val createModifier: Modifier = className.addClass
  }

  object ProgressBarStyle {
    private def style(style: String): ProgressBarStyle = new ProgressBarStyle {
      val className = s"progress-bar-$style"
    }

    lazy val success: ProgressBarStyle = style("success")
    lazy val info: ProgressBarStyle = style("info")
    lazy val warning: ProgressBarStyle = style("warning")
    lazy val danger: ProgressBarStyle = style("danger")

    lazy val striped: ProgressBarStyle = style("striped")
    lazy val animated: ProgressBarStyle = new ProgressBarStyle {
      val className = "active"
    }
  }
}

