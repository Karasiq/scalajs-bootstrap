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
    def apply[T](items: Rx[Seq[T]], columns: Rx[Seq[TableCol[T, _]]],
                 rowModifiers: T ⇒ Modifier = (_: T) ⇒ (),
                 filterItem: (T, String) ⇒ Boolean = (i: T, f: String) ⇒ i.toString.contains(f)): SortableTable[T] = {
      Builder(items, columns, rowModifiers, filterItem).createTable()
    }

    final case class Builder[T](items: Rx[Seq[T]], columns: Rx[Seq[TableCol[T, _]]] = Var(Nil),
                                rowModifiers: T ⇒ Modifier = (_: T) ⇒ (),
                                filterItem: (T, String) ⇒ Boolean = (i: T, f: String) ⇒ i.toString.contains(f)) {

      def withItems(items: Rx[Seq[T]]) = copy(items)
      def withItems(items: T*) = copy(Var(items))
      def withColumns(columns: Rx[Seq[TableCol[T, _]]]) = copy(columns = columns)
      def withColumns(columns: TableCol[T, _]*) = copy(columns = Var(columns.asInstanceOf[GenTableCols[T]]))
      def withRowModifiers(rowModifiers: T ⇒ Modifier) = copy(rowModifiers = rowModifiers)
      def withFilter(filterItem: (T, String) ⇒ Boolean) = copy(filterItem = filterItem)

      def createTable(): SortableTable[T] = {
        new UniversalSortableTable[T] {
          val items = Builder.this.items

          val columns = Builder.this.columns.asInstanceOf[Rx[GenTableCols[T]]]
          val sortByColumn = Var(columns.now.head)
          val reverseOrdering = Var(false)

          val filter = Var("")
          def filterItem(item: T, filter: String): Boolean = Builder.this.filterItem(item, filter)
          def rowModifiers(item: T): Modifier = Builder.this.rowModifiers(item)
        }
      }
    }
  }

  trait UniversalSortableTable[T] extends AbstractSortableTable[T] with BootstrapHtmlComponent {
    protected lazy val hideFilterRx = Rx(items().lengthCompare(1) <= 0)

    lazy val pagedTable = {
      val heading = Rx {
        val columns = this.columns()
        columns.map { column ⇒
          val icon = Rx[Frag] {
            if (sortByColumn() == column) span(Icon(if (reverseOrdering()) "caret-down" else "caret-up"))
            else Bootstrap.noContent
          }

          span(icon, column.name, cursor.pointer, onclick := Callback.onClick(_ ⇒ setOrdering(column)))
        }
      }

      val content = Rx {
        val columns = this.columns()
        val items = this.items()

        val filter = this.filter()
        val filteredItems = if (hideFilterRx() || filter.isEmpty) items else items.filter(item ⇒ filterItem(item, filter))

        val selectedCol = this.sortByColumn()
        val ordering = if (reverseOrdering()) selectedCol.ord.reverse else selectedCol.ord
        val sortedItems = filteredItems.sortBy(item ⇒ selectedCol.extract(item))(ordering)

        sortedItems.map(item ⇒ TableRow(columns.map(col ⇒ col.render(item)), rowModifiers(item)))
      }

      PagedTable(heading, content)
    }

    def renderTag(md: ModifierT*): TagT = {
      div(
        GridSystem.mkRow(Form(FormInput.text("", filter.reactiveInput)), hideFilterRx.reactiveHide),
        GridSystem.mkRow(pagedTable.renderTag(md:_*))
      )
    }
  }
}
