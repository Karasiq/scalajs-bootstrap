package com.karasiq.bootstrap.table

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ClassModifier

import scalatags.JsDom.all._

object TableStyles {
  private def style(st: String): ClassModifier = new ClassModifier {
    override def classMod: Modifier = s"table-$st".addClass
  }

  def striped: ClassModifier = style("striped")
  def hover: ClassModifier = style("hover")
  def bordered: ClassModifier = style("bordered")
  def condensed: ClassModifier = style("condensed")
}
