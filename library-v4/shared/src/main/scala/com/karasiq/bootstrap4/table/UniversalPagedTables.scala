package com.karasiq.bootstrap4.table

import rx.{Rx, Var}

import com.karasiq.bootstrap4.components.BootstrapComponents
import com.karasiq.bootstrap4.context.RenderingContext
import com.karasiq.bootstrap4.pagination.PageSelectors
import com.karasiq.bootstrap4.utils.Utils

trait UniversalPagedTables extends PagedTables { self: RenderingContext with BootstrapComponents with PageSelectors with Tables with Utils ⇒
  import scalaTags.all._

  type PagedTable = UniversalPagedTable
  object PagedTable extends PagedTableFactory {
    def apply(heading: Rx[Seq[Modifier]], content: Rx[Seq[TableRow]], perPage: Int = 20): PagedTable = {
      new UniversalStaticPagedTableImpl(heading, content, perPage)
    }
  }

  trait UniversalPagedTable extends AbstractPagedTable with BootstrapHtmlComponent {
    override def renderTag(md: ModifierT*): TagT = {
      div(div(Bootstrap.textAlign.center, pageSelector), table.renderTag(md:_*))
    }
  }

  protected final class UniversalStaticPagedTableImpl(val heading: Rx[Seq[Modifier]],
                                                          val allContent: Rx[Seq[TableRow]],
                                                          val rowsPerPage: Int)
    extends UniversalPagedTable with AbstractStaticPagedTable {

    val currentPage = Var(1)

    val table: Table = Table(heading, PagedTable.pagedDataRx(allContent, currentPage, rowsPerPage))
    val pageSelector: PageSelector = PageSelector(PagedTable.pagesRx(allContent, rowsPerPage), currentPage)

    pageSelector.pages.triggerLater {
      if (currentPage.now > pageSelector.pages.now) currentPage.update(pageSelector.pages.now)
    }
  }
}
