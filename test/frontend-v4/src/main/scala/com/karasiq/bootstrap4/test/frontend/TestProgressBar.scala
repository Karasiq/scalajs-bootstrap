package com.karasiq.bootstrap4.test.frontend

import scala.concurrent.duration._

import rx._
import rx.async._

import com.karasiq.bootstrap4.Bootstrap.default._
import scalaTags.all._

final class TestProgressBar(style: Modifier, updateInterval: FiniteDuration) extends BootstrapComponent {
  override def render(md: ModifierT*): ModifierT = {
    val progressBarValue = Var(0)
    val progressBar = ProgressBar.withLabel(progressBarValue).renderTag(style, ProgressBarStyle.striped, ProgressBarStyle.animated, md).render

    implicit val scheduler = new AsyncScheduler
    val timer = Timer(updateInterval)
    timer.foreach { _ ⇒
      if (progressBarValue.now < 100) {
        progressBarValue.update(progressBarValue.now + 1)
      } else {
        val alert = Alert(AlertStyle.success,
          strong("Testing"), " has finished. ",
          Alert.link(href := "https://getbootstrap.com/components/#alerts", target := "blank", "Alert inline link.")
        )
        progressBar.parentNode.replaceChild(alert.render, progressBar)
        progressBarValue.kill()
        timer.kill()
      }
    }

    progressBar
  }
}
