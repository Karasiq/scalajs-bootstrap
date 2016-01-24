package com.karasiq.bootstrap.table

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ClassModifier

import scalatags.JsDom.all._

sealed trait TableStyle extends ClassModifier

object TableStyle {
  private def style(st: String): TableStyle = new TableStyle {
    override def classMod: Modifier = s"table-$st".addClass
  }

  def striped: TableStyle = style("striped")
  def hover: TableStyle = style("hover")
  def bordered: TableStyle = style("bordered")
  def condensed: TableStyle = style("condensed")
}
