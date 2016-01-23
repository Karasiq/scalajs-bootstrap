package com.karasiq.bootstrap.table

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

  def unapply(tr: TableRow): Option[(Seq[Modifier], Modifier)] = {
    Some(tr.columns → tr.modifiers)
  }
}
