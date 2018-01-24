package com.karasiq.bootstrap.table

import scala.language.postfixOps

import rx.{Rx, Var}

import com.karasiq.bootstrap.context.{ClassModifiers, RenderingContext}
import com.karasiq.bootstrap.utils.Utils

trait Tables extends TableRows with TableStyles { self: RenderingContext with ClassModifiers with Utils ⇒
  import scalaTags.all._

  type Table <: AbstractTable with BootstrapHtmlComponent
  val Table: TableFactory

  trait AbstractTable {
    def heading: Rx[Seq[Modifier]]
    def content: Rx[Seq[TableRow]]
  }

  trait TableFactory {
    def apply(heading: Rx[Seq[Modifier]], content: Rx[Seq[TableRow]]): Table

    def static(heading: Seq[Modifier], content: Seq[TableRow]): Table = {
      apply(Var(heading), Var(content))
    }
  }
}
