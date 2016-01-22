package com.karasiq.bootstrap.table

import org.scalajs.dom
import rx._

import scala.scalajs.js
import scalatags.JsDom.all._

abstract class PagedTable extends Table {
  def getPageContent(page: Int): Seq[TableRow]

  val currentPage = Var(1)

  private val controls: js.Dictionary[dom.Element] = js.Dictionary.empty

  def setPages(pages: Int): Unit = {
    def classForPage(page: Int): String = {
      if (page == currentPage()) {
        "active"
      } else {
        ""
      }
    }

    controls.clear()
    pagination.innerHTML = ""

    // Previous page
    pagination.appendChild(li(a(href := "javascript:void(0);", onclick := { () ⇒
      if (currentPage() > 1) currentPage.update(currentPage() - 1)
    }, raw("&lsaquo;"))).render)

    // Page numbers
    (1 to pages).foreach { page ⇒
      val control = li(`class` := classForPage(page), a(href := "javascript:void(0);", onclick := { () ⇒ currentPage.update(page) }, page)).render
      controls.update(page.toString, control)
      pagination.appendChild(control)
    }

    // Next page
    pagination.appendChild(li(a(href := "javascript:void(0);", onclick := { () ⇒
      if (currentPage() < pages) currentPage.update(currentPage() + 1)
    }, raw("&rsaquo;"))).render)
  }

  val pagination: dom.Element = ul(`class` := "pagination").render

  // Init
  setPages(1)

  private val pageChanger = Obs(currentPage, "table-page-changer") {
    controls.foreach(_._2.classList.remove("active"))
    val pageControl = controls.get(currentPage().toString)
    pageControl.foreach(_.classList.add("active"))
    this.setContent(getPageContent(currentPage()))
  }

  def destroy(): Unit = {
    pageChanger.kill()
  }
}

object PagedTable {
  def apply(perPage: Int, content: Seq[TableRow]): PagedTable = {
    val pageCount = if (content.isEmpty) {
      1
    } else if (content.length % perPage == 0) {
      content.length / perPage
    } else {
      content.length / perPage + 1
    }

    new PagedTable {
      override def getPageContent(page: Int): Seq[TableRow] = {
        content.slice(perPage * (page - 1), perPage * (page - 1) + perPage)
      }

      setPages(pageCount)
    }
  }
}
