package com.karasiq.bootstrap.test.frontend

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.table.{PagedTable, TableRow, TableRowStyle, TableStyle}
import com.karasiq.bootstrap.{Bootstrap, BootstrapComponent}
import rx._

import scalatags.JsDom.all._

final class TestTable(implicit ctx: Ctx.Owner) extends BootstrapComponent {
  override def render(md: Modifier*): Modifier = {
    // Table content
    val reactiveColumn = Var(2)
    val heading = Var(Seq[Modifier]("First", "Second", "Third"))
    val content = Var(for (i <- 1 to 45) yield TableRow(Seq(i, i + 1, Rx(i + reactiveColumn())), onclick := Bootstrap.jsClick { row ⇒
      reactiveColumn.update(reactiveColumn.now + 1)
      TableRowStyle.success.applyTo(row)
    }))

    // Render table
    val pagedTable = PagedTable(heading, content, 10)
    val renderedTable = pagedTable.renderTag(TableStyle.bordered, TableStyle.hover, TableStyle.striped, md).render

    // Test reactive components
    pagedTable.currentPage.update(2)
    content.update(content.now.reverse)
    heading.update(Seq("Eins", "Zwei", Rx("Drei " + reactiveColumn())))
    renderedTable
  }
}
