package com.karasiq.bootstrap.table

import com.karasiq.bootstrap.BootstrapHtmlComponent
import com.karasiq.bootstrap.BootstrapImplicits._
import org.scalajs.dom
import rx._

import scalatags.JsDom.all._

abstract class Table(implicit ctx: Ctx.Owner) extends BootstrapHtmlComponent[dom.html.Div] {
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

  override def renderTag(md: Modifier*): RenderedTag = {
    div("table-responsive".addClass)(
      table("table".addClass, tableHead, tableBody, md)
    )
  }
}
