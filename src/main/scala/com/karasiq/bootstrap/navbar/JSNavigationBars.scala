package com.karasiq.bootstrap.navbar

import com.karasiq.bootstrap.context.JSRenderingContext

import scala.language.postfixOps

trait JSNavigationBars { self: JSRenderingContext with NavigationBars ⇒
  implicit class JSNavigation(nav: NavComponent) {
    import org.scalajs.jquery.jQuery

    /**
      * Selects tab by ID
      * @param id Tab ID
      */
    def selectTab(id: String): Unit = {
      jQuery(s"a[data-target='#${nav.navId}-$id-tab']").tab("show")
    }

    /**
      * Selects tab by index
      * @param i Tab index, starting from `0`
      */
    def selectTab(i: Int): Unit = {
      val tabs = nav.navTabs.now
      require(i >= 0 && tabs.length > i, s"Invalid tab index: $i")
      this.selectTab(tabs(i).id)
    }
  }
}
