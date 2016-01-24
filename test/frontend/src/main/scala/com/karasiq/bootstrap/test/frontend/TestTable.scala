package com.karasiq.bootstrap.test.frontend

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.table.{PagedTable, TableRow, TableStyle}
import com.karasiq.bootstrap.{Bootstrap, BootstrapComponent}
import rx._

import scalatags.JsDom.all._

final class TestTable extends BootstrapComponent {
  override def render: Modifier = {
    // Table content
    val reactiveColumn = Var(2)
    val heading = Var(Seq[Modifier]("First", "Second", "Third"))
    val content = Var(for (i <- 1 to 45) yield TableRow(Seq(i, i + 1, Rx(i + reactiveColumn())), onclick := Bootstrap.jsClick { row ⇒
      reactiveColumn.update(reactiveColumn() + 1)
      row.classList.add("success")
    }))

    // Render table
    val pagedTable = PagedTable(heading, content, 10)
    val renderedTable = pagedTable.renderTag(TableStyle.bordered, TableStyle.hover, TableStyle.striped).render

    // Test reactive components
    pagedTable.currentPage.update(2)
    content.update(content().reverse)
    heading.update(Seq("Eins", "Zwei", Rx("Drei " + reactiveColumn())))
    renderedTable
  }
}
