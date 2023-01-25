package com.karasiq.bootstrap4.pagination

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
        `class`    := "page-link",
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
        `class`    := "page-link",
        href       := "#",
        aria.label := "Next",
        onclick := Callback.onClick { _ ⇒
          if (currentPage.now < pages.now) currentPage.update(currentPage.now + 1)
        },
        span(aria.hidden := true, raw("&raquo;"))
      )
    }

    def pageLink(page: Int): TagT = {
      a(`class` := "page-link", href := "#", onclick := Callback.onClick(_ ⇒ currentPage.update(page)), page)
    }

    private[this] def previousPageButton: TagT = {
      li(
        `class` := "page-item",
        previousLink,
        "disabled".classIf(Rx(currentPage() == 1))
      )
    }

    private[this] def nextPageButton: TagT = {
      li(
        `class` := "page-item",
        nextLink,
        "disabled".classIf(Rx(currentPage() == pages()))
      )
    }

    private[this] def pageButton(page: Int): TagT = {
      li(
        `class` := "page-item",
        "active".classIf(Rx(page == currentPage())),
        pageLink(page)
      )
    }

    def renderTag(md: ModifierT*): TagT = {
      val nav = tag("nav")
      nav(
        Rx(
          ul(`class` := "pagination justify-content-center", Rx(pages() == 1).reactiveHide, md)(
            previousPageButton,
            for (page ← 1 to pages()) yield pageButton(page),
            nextPageButton
          )
        )
      )
    }
  }
}
