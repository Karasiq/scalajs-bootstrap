package com.karasiq.bootstrap_text.table

import com.karasiq.bootstrap_text.BootstrapImplicits._

import scalatags.Text.all._

abstract class PagedTable extends Table {
  def currentPage: Int
  def pages: Int

  protected def pagination = new Pagination(pages, currentPage)

  override def renderTag(md: Modifier*): RenderedTag = {
    div(div(textAlign.center, pagination), super.renderTag(md))
  }
}

/**
  * Table with pagination
  */
object PagedTable {
  final class StaticPagedTable(val heading: Seq[Modifier], val contentProvider: Seq[TableRow],
                               val perPage: Int, val currentPage: Int) extends PagedTable {
    override val content: Seq[TableRow] = {
      val data = contentProvider
      val page = currentPage
      data.slice(perPage * (page - 1), perPage * (page - 1) + perPage)
    }

    override val pages: Int = {
      val data = contentProvider
      val result = if (data.isEmpty) {
        1
      } else if (data.length % perPage == 0) {
        data.length / perPage
      } else {
        data.length / perPage + 1
      }
      result
    }
  }

  def apply(heading: Seq[Modifier], content: Seq[TableRow], perPage: Int = 20, currentPage: Int = 1): StaticPagedTable = {
    new StaticPagedTable(heading, content, perPage, currentPage)
  }
}
