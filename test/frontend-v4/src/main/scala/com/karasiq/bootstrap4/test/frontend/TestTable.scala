package com.karasiq.bootstrap4.test.frontend

import rx._

import com.karasiq.bootstrap4.Bootstrap.default._
import scalaTags.all._

object TestTable {
  def apply(): TestTable = {
    new TestTable()
  }
}

final class TestTable extends BootstrapComponent {
  override def render(md: ModifierT*): ModifierT = {
    // Table content
    val reactiveColumn = Var(2)
    val heading = Var(Seq[Modifier]("First", "Second", "Third"))
    val content = Var(for (i <- 1 to 45) yield TableRow(Seq(i, i + 1, Rx(i + reactiveColumn())), onclick := Callback.onClick { row ⇒
      reactiveColumn.update(reactiveColumn.now + 1)
      row.classList.add("success")
    }))

    // Render table
    val pagedTable = PagedTable(heading, content, 10)
    val renderedTable = pagedTable.renderTag(TableStyle.bordered, TableStyle.hover, TableStyle.striped, md).render

    // Test reactive components
    pagedTable.pageSelector.currentPage() = 2
    content.update(content.now.reverse)
    heading.update(Seq("Eins", "Zwei", Rx("Drei " + reactiveColumn())))
    renderedTable
  }
}
