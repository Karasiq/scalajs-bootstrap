package com.karasiq.bootstrap.table

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ModifierFactory

import scalatags.JsDom.all._

final class TableStyle private[table] (style: String) extends ModifierFactory {
  override def createModifier: Modifier = s"table-$style".addClass
}

object TableStyle {
  def striped = new TableStyle("striped")
  def hover = new TableStyle("hover")
  def bordered = new TableStyle("bordered")
  def condensed = new TableStyle("condensed")
}
