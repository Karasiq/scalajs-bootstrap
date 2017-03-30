package com.karasiq.bootstrap.table

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap.utils.ClassModifiers
import rx._

import scala.language.postfixOps

trait PagedTables { self: RenderingContext with BootstrapComponents with Tables with ClassModifiers ⇒
  import scalaTags.all._

  trait PagedTable extends Table {
    def currentPage: Var[Int]
    def pages: Rx[Int]

    protected def pagination: Pagination = {
      Pagination(pages, currentPage)
    }

    override def renderTag(md: Modifier*): Tag = {
      div(div(textAlign.center, pagination), super.renderTag(md))
    }
  }

  class StaticPagedTable(val heading: Rx[Seq[Modifier]],
                         val allContent: Rx[Seq[TableRow]],
                         val perPage: Int) extends PagedTable {
    override val currentPage: Var[Int] = Var(1)

    override val content: Rx[Seq[TableRow]] = Rx {
      val data = allContent()
      val page = currentPage()
      data.slice(perPage * (page - 1), perPage * (page - 1) + perPage)
    }

    override val pages: Rx[Int] = Rx {
      val data = allContent()
      val result = if (data.isEmpty) {
        1
      } else if (data.length % perPage == 0) {
        data.length / perPage
      } else {
        data.length / perPage + 1
      }
      if (currentPage.now > result) {
        currentPage.update(result)
      }
      result
    }
  }
  /**
    * Table with pagination
    */
  object PagedTable {
    def apply(heading: Rx[Seq[Modifier]], content: Rx[Seq[TableRow]], perPage: Int = 20): StaticPagedTable = {
      new StaticPagedTable(heading, content, perPage)
    }

    def static(heading: Seq[Modifier], content: Seq[TableRow], perPage: Int = 20): StaticPagedTable = {
      this.apply(Rx(heading), Rx(content), perPage)
    }
  }

  class Pagination(pages: Rx[Int], currentPage: Var[Int]) extends BootstrapComponent {
    def previousLink: Tag = {
      a(href := "#", aria.label := "Previous", onclick := Callback.onClick { _ ⇒
        if (currentPage.now > 1) currentPage.update(currentPage.now - 1)
      }, span(aria.hidden := true, raw("&laquo;")))
    }

    def nextLink: Tag = {
      a(href := "#", aria.label := "Next", onclick := Callback.onClick { _ ⇒
        if (currentPage.now < pages.now) currentPage.update(currentPage.now + 1)
      }, span(aria.hidden := true, raw("&raquo;")))
    }

    def pageLink(page: Int): Tag = {
      a(href := "#", onclick := Callback.onClick(_ ⇒ currentPage.update(page)), page)
    }

    private[this] def previousPageButton: Tag = {
      li(
        previousLink,
        "disabled".classIf(Rx(currentPage() == 1))
      )
    }

    private[this] def nextPageButton: Tag = {
      li(
        nextLink,
        "disabled".classIf(Rx(currentPage() == pages()))
      )
    }

    private[this] def pageButton(page: Int): Tag = {
      li(
        "active".classIf(Rx(page == currentPage())),
        pageLink(page)
      )
    }

    override def render(md: Modifier*): Modifier = Rx {
      ul(`class` := "pagination", Rx(pages() == 1).reactiveHide, md)(
        previousPageButton,
        for(page <- 1 to pages()) yield pageButton(page),
        nextPageButton
      )
    }
  }

  object Pagination {
    def apply(pages: Rx[Int], currentPage: Var[Int]): Pagination = {
      new Pagination(pages, currentPage)
    }
  }
}
