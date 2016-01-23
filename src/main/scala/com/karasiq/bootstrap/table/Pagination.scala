package com.karasiq.bootstrap.table

import com.karasiq.bootstrap.BootstrapImplicits._
import org.scalajs.dom.Element
import rx._

import scalatags.JsDom.all._

class Pagination(pages: Rx[Int], currentPage: Var[Int]) extends Modifier {
  private val previous = Rx {
    li(
      a(href := "javascript:void(0);", aria.label := "Previous", onclick := { () ⇒
        if (currentPage.now > 1) currentPage.update(currentPage.now - 1)
      }, span(aria.hidden := true, raw("&laquo;"))),
      `class` := Rx(if (currentPage() == 1) "disabled" else "")
    )
  }

  private val next = Rx {
    li(
      a(href := "javascript:void(0);", aria.label := "Next", onclick := { () ⇒
        if (currentPage.now < pages()) currentPage.update(currentPage.now + 1)
      }, span(aria.hidden := true, raw("&raquo;"))),
      `class` := Rx(if (currentPage() == pages()) "disabled" else "")
    )
  }

  private def pageControl(page: Int): Rx[Tag] = Rx {
    li(
      `class` := Rx(if (page == currentPage()) "active" else ""),
      a(href := "javascript:void(0);", onclick := { () ⇒ currentPage.update(page) }, page)
    )
  }

  private val pagination = Rx {
    ul(`class` := "pagination")(
      previous,
      (1 to pages()).map(pageControl),
      next
    )
  }

  override def applyTo(t: Element): Unit = {
    pagination.applyTo(t)
  }
}
