package com.karasiq.bootstrap.progressbar

import com.karasiq.bootstrap.BootstrapHtmlComponent
import com.karasiq.bootstrap.BootstrapImplicits._
import org.scalajs.dom
import rx._

import scalatags.JsDom.all._

sealed trait ProgressBar extends BootstrapHtmlComponent[dom.html.Div] {
  protected def content: Modifier

  def progress: Rx[Int]

  override def renderTag(md: Modifier*): RenderedTag = {
    div("progress".addClass)(
      div("progress-bar".addClass, role := "progressbar", aria.valuenow := progress, aria.valuemin := 0, aria.valuemax := 100, style := Rx(s"width: ${progress()}%;"), content, md)
    )
  }
}

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
