package com.karasiq.bootstrap_text.table

import com.karasiq.bootstrap_text.BootstrapHtmlComponent
import com.karasiq.bootstrap_text.BootstrapImplicits._

import scalatags.Text.all._

abstract class Table extends BootstrapHtmlComponent {
  def heading: Seq[Modifier]
  def content: Seq[TableRow]

  private def tableHead = {
    thead(tr(for (h <- heading) yield th(h)))
  }

  private def tableBody = {
    tbody(for (row <- content) yield {
      tr(
        row.modifiers,
        for (col <- row.columns) yield td(col)
      )
    })
  }

  override def renderTag(md: Modifier*): RenderedTag = {
    div("table-responsive".addClass)(
      table("table".addClass, tableHead, tableBody, md)
    )
  }
}
