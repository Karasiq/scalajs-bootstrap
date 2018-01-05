package com.karasiq.bootstrap4.table

import scala.language.{higherKinds, postfixOps}

import rx._

import com.karasiq.bootstrap4.context.RenderingContext

trait SortableTables extends TableCols { self: RenderingContext ⇒
  import scalaTags.all._

  type SortableTable[T] <: AbstractSortableTable[T]
  val SortableTable: AbstractSortableTableFactory

  trait AbstractSortableTableFactory {
    def apply[T](items: Rx[Seq[T]], columns: GenTableCols[T] = Nil,
                 rowModifiers: T ⇒ Modifier = (_: T) ⇒ (),
                 filterItem: (T, String) ⇒ Boolean = (i: T, f: String) ⇒ i.toString.contains(f)): SortableTable[T]

    def apply[T](items: T*): SortableTable[T] = apply(Var(items))
  }

  trait AbstractSortableTable[T] extends BootstrapHtmlComponent {
    def items: Rx[Seq[T]]

    def columns: Rx[GenTableCols[T]]
    def sortByColumn: Var[GenTableCol[T]]
    def reverseOrdering: Var[Boolean]

    def filter: Var[String]
    def filterItem(item: T, filter: String): Boolean

    def rowModifiers(item: T): Modifier

    def setOrdering(column: GenTableCol[T]): Unit = {
      if (sortByColumn.now == column) reverseOrdering() = !reverseOrdering.now
      else sortByColumn() = column
    }
  }
}
