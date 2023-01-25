package com.karasiq.bootstrap4.table

import rx.Rx

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.RenderingContext

trait UniversalTables { self: RenderingContext with BootstrapComponents with Tables ⇒
  import scalaTags.all._

  type Table = UniversalTable
  object Table extends TableFactory {
    def apply(heading: Rx[Seq[Modifier]], content: Rx[Seq[TableRow]]): Table = {
      new StaticUniversalTable(heading, content)
    }
  }

  trait UniversalTable extends AbstractTable with BootstrapHtmlComponent {
    lazy val tableHead: Rx[TagT] = Rx {
      val scope = attr("scope")
      thead(tr(for (h ← heading()) yield th(h, scope := "col")))
    }

    lazy val tableBody: Rx[TagT] = Rx {
      tbody(for (row ← content()) yield {
        tr(
          row.modifiers,
          for (col ← row.columns) yield td(col)
        )
      })
    }

    override def renderTag(md: Modifier*): TagT = {
      div("table-responsive".addClass)(
        table("table".addClass, tableHead, tableBody, md)
      )
    }
  }

  protected class StaticUniversalTable(val heading: Rx[Seq[Modifier]], val content: Rx[Seq[TableRow]])
      extends UniversalTable
}
