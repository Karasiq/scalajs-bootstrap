package com.karasiq.bootstrap.table

import com.karasiq.bootstrap.BootstrapImplicits._
import org.scalajs.dom.Element
import rx._

import scalatags.JsDom.all._

trait Table extends Modifier {
  def heading: Rx[Seq[Modifier]]

  def content: Rx[Seq[TableRow]]

  private lazy val head: Rx[Tag] = Rx {
    thead(tr(for (h <- heading()) yield th(h)))
  }

  private lazy val body: Rx[Tag] = Rx {
    tbody(for (TableRow(data, modifiers) <- content()) yield {
      tr(
        modifiers,
        for (col <- data) yield td(col)
      )
    })
  }

  def withStyles(style: String*): Tag = {
    val cls = (Seq("table") ++ style).mkString(" ")
    table(`class` := cls)(
      head,
      body
    )
  }

  override def applyTo(t: Element): Unit = {
    withStyles().applyTo(t)
  }
}
