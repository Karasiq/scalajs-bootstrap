package com.karasiq.bootstrap.table

import com.karasiq.bootstrap.context.BootstrapBundle
import rx.Rx

import scala.language.postfixOps

trait Tables { self: BootstrapBundle ⇒
  import scalaTags.all._

  trait TableRow {
    def columns: Seq[Modifier]

    def modifiers: Modifier
  }

  sealed trait TableRowStyle extends ModifierFactory {
    def styleClass: Option[String]

    override final def createModifier: Modifier = styleClass.map(_.addClass)
  }

  //noinspection TypeAnnotation
  object TableRowStyle {
    private def style(s: String): TableRowStyle = new TableRowStyle {
      override def styleClass: Option[String] = Some(s)
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

  object TableRow {
    def apply(data: Seq[Modifier], ms: Modifier*): TableRow = new TableRow {
      override def modifiers: Modifier = ms
      override def columns: Seq[Modifier] = data
    }

    def data(data: Modifier*): TableRow = {
      apply(data)
    }
  }

  trait Table extends BootstrapHtmlComponent {
    def heading: Rx[Seq[Modifier]]
    def content: Rx[Seq[TableRow]]

    private def tableHead: Rx[Tag] = Rx {
      thead(tr(for (h <- heading()) yield th(h)))
    }

    private def tableBody: Rx[Tag] = Rx {
      tbody(for (row <- content()) yield {
        tr(
          row.modifiers,
          for (col <- row.columns) yield td(col)
        )
      })
    }

    override def renderTag(md: Modifier*): Tag = {
      div("table-responsive".addClass)(
        table("table".addClass, tableHead, tableBody, md)
      )
    }
  }

  class StaticTable(val heading: Rx[Seq[Modifier]],
                    val content: Rx[Seq[TableRow]]) extends Table

  object Table {
    def apply(heading: Rx[Seq[Modifier]], content: Rx[Seq[TableRow]]): Table = {
      new StaticTable(heading, content)
    }
  }

  final class TableStyle private[table] (style: String) extends ModifierFactory {
    val createModifier: Modifier = s"table-$style".addClass
  }

  object TableStyle {
    lazy val striped = new TableStyle("striped")
    lazy val hover = new TableStyle("hover")
    lazy val bordered = new TableStyle("bordered")
    lazy val condensed = new TableStyle("condensed")
  }
}
