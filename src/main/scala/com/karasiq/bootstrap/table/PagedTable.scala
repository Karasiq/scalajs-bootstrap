package com.karasiq.bootstrap.table

import org.scalajs.dom
import org.scalajs.dom.Element
import rx._

import scala.scalajs.js
import scalatags.JsDom.TypedTag
import scalatags.JsDom.all._

abstract class PagedTable extends Table {
  def currentPage: Var[Int]

  def pages: Rx[Int]

  def content: Rx[Seq[TableRow]]

  private val controls: js.Dictionary[dom.Element] = js.Dictionary.empty

  private val previous = li(a(href := "javascript:void(0);", aria.label := "Previous", onclick := { () ⇒
    if (currentPage() > 1) currentPage.update(currentPage() - 1)
  }, span(aria.hidden := true, raw("&laquo;")))).render

  private val next = li(a(href := "javascript:void(0);", aria.label := "Next", onclick := { () ⇒
    if (currentPage() < pages()) currentPage.update(currentPage() + 1)
  }, span(aria.hidden := true, raw("&raquo;")))).render

  final val pagination: dom.Element = ul(`class` := "pagination").render

  // Init
  private lazy val init = {
    val paginationRenderer = Obs(pages, "pagination-renderer") {
      val pages = this.pages()
      if (pages < currentPage()) {
        currentPage.update(pages)
      }
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

    val pageChanger = Obs(currentPage, "table-page-changer") {
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
    }

    val contentRenderer = Obs(content, "table-content-renderer") {
      this.setContent(content())
    }

    Seq(paginationRenderer, pageChanger, contentRenderer)
  }

  override def render(style: String*): TypedTag[Element] = {
    init // Lazy initialization
    super.render(style:_*)
  }

  def destroy(): Unit = {
    init.foreach(_.kill())
  }
}

object PagedTable {
  final class StaticPagedTable(initialData: Seq[TableRow], perPage: Int) extends PagedTable {
    val staticContent = Var(initialData)

    override val currentPage: Var[Int] = Var(1)

    override val content: Rx[Seq[TableRow]] = Rx {
      val data = staticContent()
      val page = currentPage()
      data.slice(perPage * (page - 1), perPage * (page - 1) + perPage)
    }

    override val pages: Rx[Int] = Rx {
      val data = staticContent()
      if (data.isEmpty) {
        1
      } else if (data.length % perPage == 0) {
        data.length / perPage
      } else {
        data.length / perPage + 1
      }
    }
  }

  def apply(content: Seq[TableRow], perPage: Int = 20): StaticPagedTable = {
    new StaticPagedTable(content, perPage)
  }
}
