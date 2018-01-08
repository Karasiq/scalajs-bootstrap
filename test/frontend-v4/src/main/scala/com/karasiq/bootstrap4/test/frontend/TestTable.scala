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

    val items = Var(1 to 45: Seq[Int])
    val columns = Var(TableCols[Int](
      TableCol("First", identity, i ⇒ i),
      TableCol("Second", identity, i ⇒ i + 1),
      TableCol("Third", identity, i ⇒ Rx(i + reactiveColumn()))
    ))

    // Render table
    val sortableTable = SortableTable.Builder(columns)
      .withRowModifiers(i ⇒ onclick := Callback.onClick { row ⇒
        reactiveColumn.update(reactiveColumn.now + i)
        row.classList.add(TableRowStyle.success.className)
      })
      .createTable(items)

    val renderedTable = sortableTable.renderTag(TableStyle.bordered, TableStyle.hover, TableStyle.striped, md).render

    // Test reactive components
    sortableTable.pagedTable.pageSelector.currentPage() = 2
    items() = items.now.reverse :+ 123
    columns() = TableCols[Int](
      TableCol("Eins", identity, i ⇒ i),
      TableCol("Zwei", identity, i ⇒ i + 1),
      TableCol(Rx("Drei " + reactiveColumn()), identity, i ⇒ Rx(i + reactiveColumn()))
    )
    sortableTable.setOrdering(columns.now(1))
    renderedTable
  }
}
