package com.karasiq.bootstrap.table

import com.karasiq.bootstrap.BootstrapImplicits._
import rx._

import scalatags.JsDom.all._

abstract class PagedTable(implicit ctx: Ctx.Owner) extends Table {
  def currentPage: Var[Int]

  def pages: Rx[Int]

  protected def pagination = new Pagination(pages, currentPage)

  override def renderTag(md: Modifier*): RenderedTag = {
    div(div(textAlign.center, pagination), super.renderTag(md))
  }
}

/**
  * Table with pagination
  */
object PagedTable {
  final class StaticPagedTable(val heading: Rx[Seq[Modifier]], contentProvider: Rx[Seq[TableRow]], perPage: Int)(implicit ctx: Ctx.Owner) extends PagedTable {
    override val currentPage: Var[Int] = Var(1)

    override val content: Rx[Seq[TableRow]] = Rx {
      val data = contentProvider()
      val page = currentPage()
      data.slice(perPage * (page - 1), perPage * (page - 1) + perPage)
    }

    override val pages: Rx[Int] = Rx {
      val data = contentProvider()
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

  def apply(heading: Rx[Seq[Modifier]], content: Rx[Seq[TableRow]], perPage: Int = 20)(implicit ctx: Ctx.Owner): StaticPagedTable = {
    new StaticPagedTable(heading, content, perPage)
  }

  def static(heading: Seq[Modifier], content: Seq[TableRow], perPage: Int = 20)(implicit ctx: Ctx.Owner): StaticPagedTable = {
    this.apply(Rx(heading), Rx(content), perPage)
  }
}
