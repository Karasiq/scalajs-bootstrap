package com.karasiq.bootstrap

import scalatags.JsDom.all._

trait BootstrapAttrs {
  lazy val `data-toggle` = attr("data-toggle")
  lazy val `data-target` = attr("data-target")
  lazy val `data-slide-to` = attr("data-slide-to")
  lazy val `data-ride` = attr("data-ride")
  lazy val `data-slide` = attr("data-slide")
  lazy val `data-dismiss` = attr("data-dismiss")
}

object BootstrapAttrs extends BootstrapAttrs
