package com.karasiq.bootstrap

import scalatags.JsDom.all._

trait BootstrapAttrs {
  lazy val `data-toggle` = "data-toggle".attr
  lazy val `data-target` = "data-target".attr
  lazy val `data-slide-to` = "data-slide-to".attr
  lazy val `data-ride` = "data-ride".attr
  lazy val `data-slide` = "data-slide".attr
  lazy val `data-dismiss` = "data-dismiss".attr
}

object BootstrapAttrs extends BootstrapAttrs