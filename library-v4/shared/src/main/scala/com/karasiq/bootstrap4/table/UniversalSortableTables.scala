package com.karasiq.bootstrap4.table

import rx._

import com.karasiq.bootstrap4.context.RenderingContext
import com.karasiq.bootstrap4.form.Forms
import com.karasiq.bootstrap4.grid.Grids
import com.karasiq.bootstrap4.icons.Icons
import com.karasiq.bootstrap4.utils.Utils

trait UniversalSortableTables extends SortableTables { self: RenderingContext with Tables with PagedTables with Icons with Utils with Forms with Grids ⇒
  import scalaTags.all._

  type SortableTable[T] = UniversalSortableTable[T]
  object SortableTable extends AbstractSortableTableFactory {
    def apply[T](items: Rx[Seq[T]], columns: GenTableCols[T] = Nil,
                 rowModifiers: T ⇒ Modifier = (_: T) ⇒ (),
                 filterItem: (T, String) ⇒ Boolean = (i: T, f: String) ⇒ i.toString.contains(f)): SortableTable[T] = {
      Builder(items, columns, rowModifiers, filterItem).createTable()
    }

    final case class Builder[T](items: Rx[Seq[T]], columns: GenTableCols[T] = Nil,
                                rowModifiers: T ⇒ Modifier = (_: T) ⇒ (),
                                filterItem: (T, String) ⇒ Boolean = (i: T, f: String) ⇒ i.toString.contains(f)) {

      def withItems(items: Rx[Seq[T]]) = copy(items)
      def withItems(items: T*) = copy(Var(items))
      def withColumns(columns: TableCol[T, _]*) = copy(columns = columns.asInstanceOf[GenTableCols[T]])
      def withRowModifiers(rowModifiers: T ⇒ Modifier) = copy(rowModifiers = rowModifiers)
      def withFilter(filterItem: (T, String) ⇒ Boolean) = copy(filterItem = filterItem)

      def createTable(): SortableTable[T] = {
        require(columns.nonEmpty, "Columns is empty")

        new UniversalSortableTable[T] {
          val items = Builder.this.items

          val columns = Var(Builder.this.columns)
          val sortByColumn = Var(Builder.this.columns.head)
          val reverseOrdering = Var(false)

          val filter = Var("")
          def filterItem(item: T, filter: String): Boolean = Builder.this.filterItem(item, filter)
          def rowModifiers(item: T): Modifier = Builder.this.rowModifiers(item)
        }
      }
    }
  }

  trait UniversalSortableTable[T] extends AbstractSortableTable[T] {
    def renderTag(md: ModifierT*): TagT = {
      val hideFilter = Rx(items().lengthCompare(1) <= 0)

      val heading = Rx {
        val columns = this.columns()
        columns.map { column ⇒
          val icon = Rx[Frag] {
            if (sortByColumn() == column) Icon(if (reverseOrdering()) "caret-down" else "caret-up")
            else Bootstrap.noContent
          }

          span(icon, column.name, cursor.pointer, onclick := Callback.onClick(_ ⇒ setOrdering(column)))
        }
      }

      val content = Rx {
        val columns = this.columns()
        val items = this.items()

        val filter = this.filter()
        val filteredItems = if (hideFilter() || filter.isEmpty) items else items.filter(item ⇒ filterItem(item, filter))

        val selectedCol = this.sortByColumn()
        val ordering = if (reverseOrdering()) selectedCol.ord.reverse else selectedCol.ord
        val sortedItems = filteredItems.sortBy(item ⇒ selectedCol.extract(item))(ordering)

        sortedItems.map(item ⇒ TableRow(columns.map(col ⇒ col.render(item)), rowModifiers(item)))
      }

      div(
        GridSystem.mkRow(Form(FormInput.text("", filter.reactiveInput)), hideFilter.reactiveHide),
        GridSystem.mkRow(PagedTable(heading, content).renderTag(md:_*))
      )
    }
  }
}
