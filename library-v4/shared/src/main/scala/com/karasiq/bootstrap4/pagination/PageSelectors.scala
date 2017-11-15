package com.karasiq.bootstrap4.pagination

import rx.{Rx, Var}

import com.karasiq.bootstrap4.context.RenderingContext

trait PageSelectors { self: RenderingContext ⇒
  type PageSelector <: AbstractPageSelector with BootstrapHtmlComponent
  val PageSelector: PageSelectorFactory

  trait AbstractPageSelector {
    def pages: Rx[Int]
    def currentPage: Var[Int]
  }

  trait PageSelectorFactory {
    def apply(pages: Rx[Int], currentPage: Var[Int] = Var(1)): PageSelector
  }
}
