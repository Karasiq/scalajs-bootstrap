package com.karasiq.bootstrap.table

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ClassModifier

import scalatags.JsDom.all._

sealed trait TableStyle extends ClassModifier

object TableStyle {
  private def style(st: String): TableStyle = new TableStyle {
    override def classMod: Modifier = s"table-$st".addClass
  }

  def striped = style("striped")
  def hover = style("hover")
  def bordered = style("bordered")
  def small = style("sm")
  def inverse = style("inverse")
  def reflow = style("reflow")
}
