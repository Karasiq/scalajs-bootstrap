package com.karasiq.bootstrap4.progressbar

import scala.language.postfixOps

import rx.Rx

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.{ReactiveBinds, ReactiveImplicits, RenderingContext}

trait ProgressBars extends ProgressBarStyles {
  self: RenderingContext with BootstrapComponents with ReactiveBinds with ReactiveImplicits ⇒

  type ProgressBar <: AbstractProgressBar with BootstrapHtmlComponent
  val ProgressBar: ProgressBarFactory

  trait AbstractProgressBar {
    def progress: Rx[Int]
  }

  /** Provide up-to-date feedback on the progress of a workflow or action with simple yet flexible progress bars.
    * @see
    *   [[http://getbootstrap.com/components/#progress]]
    */
  trait ProgressBarFactory {
    def basic(value: Rx[Int]): ProgressBar
    def withLabel(value: Rx[Int]): ProgressBar
  }
}
