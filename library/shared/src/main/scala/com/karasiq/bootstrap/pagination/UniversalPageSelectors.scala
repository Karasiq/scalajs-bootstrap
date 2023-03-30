package com.karasiq.bootstrap.pagination

import rx.{Rx, Var}

import com.karasiq.bootstrap.context.RenderingContext

trait UniversalPageSelectors extends PageSelectors { self: RenderingContext ⇒
  import scalaTags.all._

  type PageSelector = UniversalPageSelector
  object PageSelector extends PageSelectorFactory {
    def apply(pages: Rx[Int], currentPage: Var[Int]): PageSelector = {
      new UniversalPageSelector(pages, currentPage)
    }
  }

  class UniversalPageSelector(val pages: Rx[Int], val currentPage: Var[Int])
      extends AbstractPageSelector
      with BootstrapHtmlComponent {

    def previousLink: TagT = {
      a(
        href       := "#",
        aria.label := "Previous",
        onclick := Callback.onClick { _ ⇒
          if (currentPage.now > 1) currentPage.update(currentPage.now - 1)
        },
        span(aria.hidden := true, raw("&laquo;"))
      )
    }

    def nextLink: TagT = {
      a(
        href       := "#",
        aria.label := "Next",
        onclick := Callback.onClick { _ ⇒
          if (currentPage.now < pages.now) currentPage.update(currentPage.now + 1)
        },
        span(aria.hidden := true, raw("&raquo;"))
      )
    }

    def pageLink(page: Int): TagT = {
      a(href := "#", onclick := Callback.onClick(_ ⇒ currentPage.update(page)), page)
    }

    private[this] def previousPageButton: TagT = {
      li(
        previousLink,
        "disabled".classIf(Rx(currentPage() == 1))
      )
    }

    private[this] def nextPageButton: TagT = {
      li(
        nextLink,
        "disabled".classIf(Rx(currentPage() == pages()))
      )
    }

    private[this] def pageButton(page: Int): TagT = {
      li(
        "active".classIf(Rx(page == currentPage())),
        pageLink(page)
      )
    }

    def renderTag(md: ModifierT*): TagT = {
      div(
        Rx(
          ul(`class` := "pagination", Rx(pages() == 1).reactiveHide, md)(
            previousPageButton,
            for (page ← 1 to pages()) yield pageButton(page),
            nextPageButton
          )
        )
      )
    }
  }
}
