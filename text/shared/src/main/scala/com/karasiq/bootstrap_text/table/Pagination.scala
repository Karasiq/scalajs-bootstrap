package com.karasiq.bootstrap_text.table

import com.karasiq.bootstrap_text.BootstrapComponent
import com.karasiq.bootstrap_text.BootstrapImplicits._

import scalatags.Text.all._

class Pagination(pages: Int, currentPage: Int) extends BootstrapComponent {
  def pageLink(page: Int): Tag = {
    a(href := "#", page)
  }

  def prevLink: Tag = {
    a(href := "#", aria.label := "Previous", span(aria.hidden := true, raw("&laquo;")))
  }

  def nextLink: Tag = {
    a(href := "#", aria.label := "Next", span(aria.hidden := true, raw("&raquo;")))
  }

  private def previousPageButton: Tag = {
    li(
      prevLink,
      "disabled".classIf(currentPage == 1)
    )
  }

  private def nextPageButton: Tag = {
    li(
      nextLink,
      "disabled".classIf(currentPage == pages)
    )
  }

  private def pageButton(page: Int): Tag = {
    li(
      "active".classIf(page == currentPage),
      pageLink(page)
    )
  }

  override def render(md: Modifier*): Modifier = {
    ul(`class` := "pagination", if (pages == 1) display.none else (), md)(
      previousPageButton,
      for(page <- 1 to pages) yield pageButton(page),
      nextPageButton
    )
  }
}
