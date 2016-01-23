package com.karasiq.bootstrap.table

import com.karasiq.bootstrap.BootstrapImplicits._
import org.scalajs.dom.Element
import rx._

import scalatags.JsDom.all._

class Pagination(pages: Rx[Int], currentPage: Var[Int]) extends Modifier {
  private def previousPageButton: Tag = {
    li(
      a(href := "javascript:void(0);", aria.label := "Previous", onclick := { () ⇒
        if (currentPage.now > 1) currentPage.update(currentPage.now - 1)
      }, span(aria.hidden := true, raw("&laquo;"))),
      `class` := Rx(if (currentPage() == 1) "disabled" else "")
    )
  }

  private def nextPageButton: Tag = {
    li(
      a(href := "javascript:void(0);", aria.label := "Next", onclick := { () ⇒
        if (currentPage.now < pages()) currentPage.update(currentPage.now + 1)
      }, span(aria.hidden := true, raw("&raquo;"))),
      `class` := Rx(if (currentPage() == pages()) "disabled" else "")
    )
  }

  private def pageButton(page: Int): Tag = {
    li(
      `class` := Rx(if (page == currentPage()) "active" else ""),
      a(href := "javascript:void(0);", onclick := { () ⇒ currentPage.update(page) }, page)
    )
  }

  private def pagination: Tag = {
    ul(`class` := "pagination")(
      previousPageButton,
      for(page <- 1 to pages()) yield pageButton(page),
      nextPageButton
    )
  }

  override def applyTo(t: Element): Unit = {
    pagination.applyTo(t)
  }
}
