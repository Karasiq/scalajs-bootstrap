package com.karasiq.bootstrap.alert

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ModifierFactory

import scalatags.JsDom.all._

final class AlertStyle private[alert] (name: String) extends ModifierFactory {
  val createModifier: Modifier = s"alert-$name".addClass
}

object AlertStyle {
  lazy val success = new AlertStyle("success")
  lazy val info = new AlertStyle("info")
  lazy val warning = new AlertStyle("warning")
  lazy val danger = new AlertStyle("danger")
}