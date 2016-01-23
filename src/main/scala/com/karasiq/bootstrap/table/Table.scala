package com.karasiq.bootstrap.table

import com.karasiq.bootstrap.BootstrapHtmlComponent
import com.karasiq.bootstrap.BootstrapImplicits._
import org.scalajs.dom
import rx._

import scalatags.JsDom.all._

trait Table extends BootstrapHtmlComponent[dom.html.Element] {
  def heading: Rx[Seq[Modifier]]

  def content: Rx[Seq[TableRow]]

  private def tableHead: Rx[Tag] = Rx {
    thead(tr(for (h <- heading()) yield th(h)))
  }

  private def tableBody: Rx[Tag] = Rx {
    tbody(for (TableRow(data, modifiers) <- content()) yield {
      tr(
        modifiers,
        for (col <- data) yield td(col)
      )
    })
  }

  override def renderTag(md: Modifier*): RenderedTag = {
    table("table".addClass, tableHead, tableBody, md)
  }
}
