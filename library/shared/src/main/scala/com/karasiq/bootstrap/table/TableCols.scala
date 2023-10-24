package com.karasiq.bootstrap.table

import scala.language.implicitConversions

import com.karasiq.bootstrap.context.RenderingContext

trait TableCols { self: RenderingContext ⇒
  import scalaTags.all._

  case class TableCol[T, V](name: Modifier, extract: T ⇒ V, render: T ⇒ Modifier)(implicit val ord: Ordering[V])
  object TableCol {
    implicit def toGenTableCol[T](c: TableCol[T, _]) = c.asInstanceOf[GenTableCol[T]]
  }

  object TableCols {
    def apply[T](cols: TableCol[T, _]*) = cols
  }

  type GenTableCol[T]  = TableCol[T, Any]
  type GenTableCols[T] = Seq[GenTableCol[T]]
}
