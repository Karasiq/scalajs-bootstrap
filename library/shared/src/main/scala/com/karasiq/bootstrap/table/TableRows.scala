package com.karasiq.bootstrap.table

import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap.utils.ClassModifiers

trait TableRows { self: RenderingContext with ClassModifiers ⇒
  import scalaTags.all._

  trait TableRow {
    def columns: Seq[Modifier]
    def modifiers: Modifier
  }

  sealed trait TableRowStyle extends ModifierFactory {
    def styleClass: Option[String]
    override final def createModifier: ModifierT = styleClass.map(_.addClass)
  }

  //noinspection TypeAnnotation
  object TableRowStyle {
    private def style(s: String): TableRowStyle = new TableRowStyle {
      override def styleClass: Option[String] = Some(s)
    }

    def default: TableRowStyle = new TableRowStyle {
      override def styleClass: Option[String] = None
    }
    def active = style("active")
    def success = style("success")
    def warning = style("warning")
    def danger = style("danger")
    def info = style("info")
  }

  object TableRow {
    def apply(data: Seq[Modifier], ms: Modifier*): TableRow = new TableRow {
      override def modifiers: Modifier = ms
      override def columns: Seq[Modifier] = data
    }

    def data(data: Modifier*): TableRow = {
      apply(data)
    }
  }
}
