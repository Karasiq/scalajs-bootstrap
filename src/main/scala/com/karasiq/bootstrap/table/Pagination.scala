package com.karasiq.bootstrap.table

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.{Bootstrap, BootstrapComponent}
import rx._

import scalatags.JsDom.all
import scalatags.JsDom.all._

class Pagination(pages: Rx[Int], currentPage: Var[Int])(implicit ctx: Ctx.Owner) extends BootstrapComponent {
  private def previousPageButton: Tag = {
    li(
      "page-item".addClass,
      a("page-link".addClass, href := "#", aria.label := "Previous", onclick := Bootstrap.jsClick { _ ⇒
        if (currentPage.now > 1) currentPage.update(currentPage.now - 1)
      }, span(aria.hidden := true, raw("&laquo;"))),
      "disabled".classIf(Rx(currentPage() == 1))
    )
  }

  private def nextPageButton: Tag = {
    li(
      "page-item".addClass,
      a("page-link".addClass, href := "#", aria.label := "Next", onclick := Bootstrap.jsClick { _ ⇒
        if (currentPage.now < pages.now) currentPage.update(currentPage.now + 1)
      }, span(aria.hidden := true, raw("&raquo;"))),
      "disabled".classIf(Rx(currentPage() == pages()))
    )
  }

  private def pageButton(page: Int): Tag = {
    li(
      "page-item".addClass,
      a("page-link".addClass, href := "#", onclick := Bootstrap.jsClick(_ ⇒ currentPage.update(page)), page),
      "active".classIf(Rx(page == currentPage()))
    )
  }

  override def render(md: all.Modifier*): Modifier = Rx {
    ul(`class` := "pagination", Rx(pages() == 1).reactiveHide, md)(
      previousPageButton,
      for(page <- 1 to pages()) yield pageButton(page),
      nextPageButton
    )
  }
}
