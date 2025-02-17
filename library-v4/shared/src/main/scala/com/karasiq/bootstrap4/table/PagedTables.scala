package com.karasiq.bootstrap4.table

import scala.language.postfixOps

import rx._

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.{ClassModifiers, RenderingContext}
import com.karasiq.bootstrap4.pagination.PageSelectors

trait PagedTables { self: RenderingContext with BootstrapComponents with Tables with PageSelectors with ClassModifiers ⇒
  import scalaTags.all._

  type PagedTable <: AbstractPagedTable with BootstrapHtmlComponent
  val PagedTable: PagedTableFactory

  /** Table with pagination
    */
  trait AbstractPagedTable {
    def table: Table
    def pageSelector: PageSelector
  }

  trait PagedTableFactory {
    def apply(heading: Rx[Seq[Modifier]], content: Rx[Seq[TableRow]], perPage: Int = 20): PagedTable

    def static(heading: Seq[Modifier], content: Seq[TableRow], perPage: Int = 20): PagedTable = {
      this.apply(Var(heading), Var(content), perPage)
    }

    private[table] def pagesRx(allContent: Rx[Seq[TableRow]], rowsPerPage: Int): Rx[Int] = {
      Rx {
        val data = allContent()
        val result = if (data.isEmpty) {
          1
        } else if (data.length % rowsPerPage == 0) {
          data.length / rowsPerPage
        } else {
          data.length / rowsPerPage + 1
        }
        result
      }
    }

    private[table] def pagedDataRx(
        allContent: Rx[Seq[TableRow]],
        currentPage: Rx[Int],
        rowsPerPage: Int
    ): Rx[Seq[TableRow]] = {
      Rx {
        val data = allContent()
        val page = currentPage()
        data.slice(rowsPerPage * (page - 1), rowsPerPage * (page - 1) + rowsPerPage)
      }
    }
  }

  trait AbstractStaticPagedTable extends AbstractPagedTable {
    def allContent: Rx[Seq[TableRow]]
    def rowsPerPage: Int
  }
}
