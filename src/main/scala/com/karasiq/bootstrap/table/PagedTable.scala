package com.karasiq.bootstrap.table

import org.scalajs.dom
import rx._

import scala.scalajs.js
import scalatags.JsDom.all._

abstract class PagedTable extends Table {
  def getPageContent(page: Int): Seq[TableRow]

  private val currentPage = Var(1)

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
    (1 to pages).foreach { page ⇒
      val control = li(`class` := classForPage(page), a(href := "", onclick := { () ⇒ currentPage.update(page) })).render
      controls.update(page.toString, control)
      pagination.appendChild(control)
    }
  }

  val pagination: dom.Element = ul(`class` := "pagination").render

  // Init
  setPages(1)

  val pageChanger = Obs(currentPage, "table-page-changer") {
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
    new PagedTable {
      override def getPageContent(page: Int): Seq[TableRow] = {
        content.slice(perPage * (page - 1), perPage * (page - 1) + perPage)
      }

      setPages(content.length / perPage + 1)
    }
  }
}
