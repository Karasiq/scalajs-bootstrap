package com.karasiq.bootstrap.alert

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ModifierFactory

import scalatags.JsDom.all._

final class AlertStyle private[alert] (name: String) extends ModifierFactory {
  override def createModifier: Modifier = s"alert-$name".addClass
}

object AlertStyle {
  def success = new AlertStyle("success")
  def info = new AlertStyle("info")
  def warning = new AlertStyle("warning")
  def danger = new AlertStyle("danger")
}