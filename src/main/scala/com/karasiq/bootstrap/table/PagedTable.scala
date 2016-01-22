package com.karasiq.bootstrap.table

import org.scalajs.dom
import rx._

import scala.scalajs.js
import scalatags.JsDom.all._

abstract class PagedTable extends Table {
  def pages: Rx[Int]

  def pageContent(page: Int): Seq[TableRow]

  final val currentPage: Var[Int] = Var(1)

  private val controls: js.Dictionary[dom.Element] = js.Dictionary.empty

  private val previous = li(a(href := "javascript:void(0);", aria.label := "Previous", onclick := { () ⇒
    if (currentPage() > 1) currentPage.update(currentPage() - 1)
  }, span(aria.hidden := true, raw("&laquo;")))).render

  private val next = li(a(href := "javascript:void(0);", aria.label := "Next", onclick := { () ⇒
    if (currentPage() < pages()) currentPage.update(currentPage() + 1)
  }, span(aria.hidden := true, raw("&raquo;")))).render

  final val pagination: dom.Element = ul(`class` := "pagination").render

  // Init
  private val paginationRenderer = Obs(pages, "pagination-renderer") {
    val pages = this.pages()
    require(pages >= 1, "Invalid pages count")

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
    pagination.appendChild(previous)

    // Page numbers
    (1 to pages).foreach { page ⇒
      val control = li(`class` := classForPage(page), a(href := "javascript:void(0);", onclick := { () ⇒ currentPage.update(page) }, page)).render
      controls.update(page.toString, control)
      pagination.appendChild(control)
    }

    // Next page
    pagination.appendChild(next)
  }

  private val pageChanger = Obs(currentPage, "table-page-changer") {
    controls.foreach(_._2.classList.remove("active"))
    val pageControl = controls.get(currentPage().toString)
    pageControl.foreach(_.classList.add("active"))

    if (currentPage() < pages()) {
      next.classList.remove("disabled")
    } else {
      next.classList.add("disabled")
    }

    if (currentPage() > 1) {
      previous.classList.remove("disabled")
    } else {
      previous.classList.add("disabled")
    }

    this.setContent(pageContent(currentPage()))
  }

  def destroy(): Unit = {
    paginationRenderer.kill()
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
      override def pages: Rx[Int] = Rx(pageCount)

      override def pageContent(page: Int): Seq[TableRow] = {
        content.slice(perPage * (page - 1), perPage * (page - 1) + perPage)
      }
    }
  }
}
