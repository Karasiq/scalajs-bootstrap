package com.karasiq.bootstrap4.table

import com.karasiq.bootstrap4.context.RenderingContext

trait TableCols { self: RenderingContext ⇒
  import scalaTags.all._

  final case class TableCol[T, V](name: Modifier, extract: T ⇒ V, render: T ⇒ Modifier)(implicit val ord: Ordering[V])
  type GenTableCol[T] = TableCol[T, Any]
  type GenTableCols[T] = Seq[GenTableCol[T]]
}
