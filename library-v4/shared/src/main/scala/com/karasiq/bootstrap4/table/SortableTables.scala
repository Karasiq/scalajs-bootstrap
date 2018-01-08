package com.karasiq.bootstrap4.table

import scala.language.{higherKinds, postfixOps}

import rx._

import com.karasiq.bootstrap4.context.RenderingContext
import com.karasiq.bootstrap4.utils.Utils

trait SortableTables extends TableCols { self: RenderingContext with PagedTables with Utils ⇒
  import scalaTags.all._

  type SortableTable[T] <: AbstractSortableTable[T] with BootstrapHtmlComponent
  val SortableTable: AbstractSortableTableFactory

  trait AbstractSortableTableFactory {
    def apply[T](items: Rx[Seq[T]], columns: Rx[Seq[TableCol[T, _]]],
                 rowModifiers: T ⇒ Modifier = (_: T) ⇒ Bootstrap.noModifier,
                 filterItem: (T, String) ⇒ Boolean = (i: T, f: String) ⇒ i.toString.contains(f)): SortableTable[T]
  }

  trait AbstractSortableTable[T] {
    def items: Rx[Seq[T]]

    def columns: Rx[GenTableCols[T]]
    def sortByColumn: Var[GenTableCol[T]]
    def reverseOrdering: Var[Boolean]

    def filter: Var[String]
    def filterItem(item: T, filter: String): Boolean

    def rowModifiers(item: T): Modifier

    def setOrdering(column: TableCol[T, _]): Unit = {
      if (sortByColumn.now == column) reverseOrdering() = !reverseOrdering.now
      else sortByColumn() = column
    }

    def pagedTable: PagedTable
  }
}
