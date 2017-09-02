package com.karasiq.bootstrap.navbar

import rx.Rx

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap.grid.Grids
import com.karasiq.bootstrap.icons.Icons
import com.karasiq.bootstrap.utils.{ClassModifiers, Utils}

trait UniversalNavigationBars { self: RenderingContext with Icons with Grids with Utils with BootstrapComponents with ClassModifiers with NavigationBars ⇒
  import scalaTags.all._

  import BootstrapAttrs._

  type Navigation = UniversalNavigation
  object Navigation extends NavigationFactory {
    def tabs(tabs: NavigationTab*): Navigation = {
      new UniversalNavigation(Rx(tabs))
    }

    def pills(tabs: NavigationTab*): Navigation = {
      new UniversalNavigation(Rx(tabs), "pills")
    }
  }

  type NavigationBar = UniversalNavigationBar
  object NavigationBar extends NavigationBarFactory {
    def create(tabs: NavigationTabs, navId: String, brand: scalaTags.all.Modifier, styles: Seq[NavigationBarStyle], container: (scalaTags.all.Modifier) ⇒ scalaTags.all.Modifier, contentContainer: (scalaTags.all.Modifier) ⇒ scalaTags.all.Modifier): NavigationBar = {
      new UniversalNavigationBar(tabs, navId, brand, styles, container, contentContainer)
    }
  }

  class UniversalNavigation(val navTabs: NavigationTabs, val navType: String = "tabs",
                            val navId: String = Bootstrap.newId)
    extends AbstractNavigation with BootstrapHtmlComponent {

    private def tabContainer = Rx {
      def renderTab(tab: NavigationTab): Tag = {
        val idLink = s"$navId-${tab.id}-tab"
        li(role := "presentation", tab.modifiers)(
          a(href := "#", aria.controls := idLink, role := "tab", `data-toggle` := "tab", `data-target` := s"#$idLink")(
            if (tab.icon != NoIcon) Seq[Modifier](tab.icon, Bootstrap.nbsp) else (),
            tab.name
          )
        )
      }

      val tabs = navTabs()
      ul(`class` := s"nav nav-$navType", role := "tablist")(
        renderTab(tabs.head)("active".addClass),
        for (t <- tabs.tail) yield renderTab(t)
      )
    }

    private def tabContentContainer = Rx {
      def renderPanel(t: NavigationTab): Tag = {
        div(role := "tabpanel", "tab-pane".addClass, "fade".addClass, id := s"$navId-${t.id}-tab")(
          t.content
        )
      }

      val tabs = navTabs()
      div("tab-content".addClass)(
        renderPanel(tabs.head)("active".addClass, "in".addClass),
        for (t <- tabs.tail) yield renderPanel(t)
      )
    }

    override def renderTag(md: Modifier*): Tag = {
      div(tabContainer, tabContentContainer, md)
    }
  }

  /**
    * Simple bootstrap navigation bar
    */
  class UniversalNavigationBar(val navTabs: NavigationTabs,
                               val navId: String,
                               brand: Modifier,
                               styles: Seq[NavigationBarStyle],
                               container: Modifier ⇒ Modifier,
                               contentContainer: Modifier ⇒ Modifier)
    extends AbstractNavigationBar with BootstrapComponent {

    private[this] val nav = tag("nav")

    private[this] val tabContainer = Rx {
      def renderTab(active: Boolean, tab: NavigationTab): Tag = {
        val idLink = s"$navId-${tab.id}-tab"
        li(
          tab.modifiers,
          "active".classIf(active),
          a(href := "#", aria.controls := idLink, role := "tab", `data-toggle` := "tab", `data-target` := s"#$idLink")(
            if (tab.icon != NoIcon) Seq[Modifier](tab.icon, Bootstrap.nbsp) else (),
            tab.name
          )
        )
      }

      val tabs = navTabs()
      ul(`class` := "nav navbar-nav")(
        renderTab(active = true, tabs.head),
        for (tab <- tabs.tail) yield renderTab(active = false, tab)
      )
    }

    private[this] val tabContentContainer = Rx {
      def renderContent(active: Boolean, tab: NavigationTab): Tag = {
        div(id := s"$navId-${tab.id}-tab", role := "tabpanel", `class` := (if (active) "tab-pane active fade in" else "tab-pane fade"))(
          tab.content
        )
      }

      val tabs = navTabs()
      div(id := s"$navId-tabcontent", `class` := "tab-content")(
        renderContent(active = true, tabs.head),
        for (tab <- tabs.tail) yield renderContent(active = false, tab)
      )
    }

    def navbar: Tag = {
      nav("navbar".addClass, styles)(
        container(Seq(
          // Header
          div(`class` := "navbar-header")(
            button(`type` := "button", `data-toggle` := "collapse", `data-target` := s"#$navId", `class` := "navbar-toggle collapsed")(
              span(`class` := "sr-only", "Toggle navigation"),
              span(`class` := "icon-bar"),
              span(`class` := "icon-bar"),
              span(`class` := "icon-bar")
            ),
            a(href := "#", `class` := "navbar-brand", brand)
          ),
          div(id := navId, `class` := "navbar-collapse collapse")(
            tabContainer
          )
        ))
      )
    }

    def content: Modifier = {
      tabContentContainer
    }

    def render(md: Modifier*): Modifier = {
      Seq(navbar, contentContainer(content)) ++ md
    }
  }
}
