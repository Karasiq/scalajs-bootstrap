package com.karasiq.bootstrap.card

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ClassModifier

import scalatags.JsDom.all._

sealed trait CardStyle extends ClassModifier {
  def styleClass: Option[String]

  override def classMod: Modifier = styleClass.classOpt
}

object CardStyle {
  def default: CardStyle = new CardStyle {
    override def styleClass: Option[String] = None
  }

  private def style(name: String): CardStyle = new CardStyle {
    override def styleClass: Option[String] = Some(s"card-$name")
  }

  def primary: CardStyle = style("primary")
  def success: CardStyle = style("success")
  def info: CardStyle = style("info")
  def warning: CardStyle = style("warning")
  def danger: CardStyle = style("danger")
}