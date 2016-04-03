package com.karasiq.bootstrap.table

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ClassModifier

import scalatags.JsDom.all._

trait TableRow {
  def columns: Seq[Modifier]
  def modifiers: Modifier
}

object TableRow {
  def apply(data: Seq[Modifier], ms: Modifier*): TableRow = new TableRow {
    override def modifiers: Modifier = ms
    override def columns: Seq[Modifier] = data
  }
}

sealed trait TableRowStyle extends ClassModifier {
  def styleClass: Option[String]
  override final def classMod: Modifier = styleClass.map(_.addClass)
}

object TableRowStyle {
  private def style(name: String): TableRowStyle = new TableRowStyle {
    override def styleClass: Option[String] = Some(s"table-$name")
  }

  def default = new TableRowStyle {
    override def styleClass: Option[String] = None
  }
  def active = style("active")
  def success = style("success")
  def warning = style("warning")
  def danger = style("danger")
  def info = style("info")
}
