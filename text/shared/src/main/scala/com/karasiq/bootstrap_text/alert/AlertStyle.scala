package com.karasiq.bootstrap_text.alert

import com.karasiq.bootstrap_text.BootstrapImplicits._
import com.karasiq.bootstrap_text.ModifierFactory

import scalatags.Text.all._

final class AlertStyle private[alert] (name: String) extends ModifierFactory {
  val createModifier: Modifier = s"alert-$name".addClass
}

object AlertStyle {
  lazy val success = new AlertStyle("success")
  lazy val info = new AlertStyle("info")
  lazy val warning = new AlertStyle("warning")
  lazy val danger = new AlertStyle("danger")
}