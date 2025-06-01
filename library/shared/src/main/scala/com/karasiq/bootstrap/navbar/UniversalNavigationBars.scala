package com.karasiq.bootstrap.navbar

import rx.Rx

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.{ClassModifiers, RenderingContext}
import com.karasiq.bootstrap.grid.Grids
import com.karasiq.bootstrap.icons.Icons
import com.karasiq.bootstrap.utils.Utils

trait UniversalNavigationBars {
  self: RenderingContext
    with Icons
    with Grids
    with Utils
    with BootstrapComponents
    with ClassModifiers
    with NavigationBars
    with NavigationBarStyles ⇒
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

  type NavigationBar = NavigationBarBuilder
  object NavigationBar extends NavigationBarFactory {
    def apply(
        tabs: Seq[NavigationTab] = Nil,
        barId: String = Bootstrap.newId,
        brand: Modifier = "Navigation",
        styles: Seq[NavigationBarStyle] = Seq(NavigationBarStyle.default, NavigationBarStyle.fixedTop),
        container: Modifier ⇒ Modifier = md ⇒ GridSystem.container(md),
        contentContainer: Modifier ⇒ Modifier = md ⇒ GridSystem.container(GridSystem.mkRow(md))
    ): NavigationBarBuilder = {
      NavigationBarBuilder(tabs, barId, brand, styles, container, contentContainer)
    }
  }

  class UniversalNavigation(
      val navTabs: NavigationTabs,
      val navType: String = "tabs",
      val navId: String = Bootstrap.newId
  ) extends AbstractNavigation
      with BootstrapHtmlComponent {

    private def tabContainer = Rx {
      def renderTab(tab: NavigationTab): Tag = {
        val idLink = this.tabId(tab.id)
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
        for (t ← tabs.tail) yield renderTab(t)
      )
    }

    private def tabContentContainer = Rx {
      def renderPanel(t: NavigationTab): Tag = {
        div(role := "tabpanel", "tab-pane".addClass, "fade".addClass, id := this.tabId(t.id))(
          t.content
        )
      }

      val tabs = navTabs()
      div("tab-content".addClass)(
        renderPanel(tabs.head)("active".addClass, "in".addClass),
        for (t ← tabs.tail) yield renderPanel(t)
      )
    }

    override def renderTag(md: ModifierT*): TagT = {
      div(tabContainer, tabContentContainer, md)
    }
  }

  /** Simple bootstrap navigation bar
    */
  class UniversalNavigationBar(
      val navTabs: NavigationTabs,
      val navId: String,
      brand: Modifier,
      styles: Seq[NavigationBarStyle],
      container: Modifier ⇒ Modifier,
      contentContainer: Modifier ⇒ Modifier
  ) extends AbstractNavigationBar
      with BootstrapComponent {

    private[this] val nav = tag("nav")

    private[this] val tabContainer = Rx {
      def renderTab(active: Boolean, tab: NavigationTab): Tag = {
        val idLink = this.tabId(tab.id)
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
        for (tab ← tabs.tail) yield renderTab(active = false, tab)
      )
    }

    private[this] val tabContentContainer = Rx {
      def renderContent(active: Boolean, tab: NavigationTab): Tag = {
        div(
          id      := this.tabId(tab.id),
          role    := "tabpanel",
          `class` := (if (active) "tab-pane active fade in" else "tab-pane fade")
        )(
          tab.content
        )
      }

      val tabs = navTabs()
      div(id := s"$navId-tabcontent", `class` := "tab-content")(
        renderContent(active = true, tabs.head),
        for (tab ← tabs.tail) yield renderContent(active = false, tab)
      )
    }

    def navbar: Tag = {
      nav("navbar".addClass, styles)(
        container(
          Seq(
            // Header
            div(`class` := "navbar-header")(
              button(
                `type`        := "button",
                `data-toggle` := "collapse",
                `data-target` := s"#$navId",
                `class`       := "navbar-toggle collapsed"
              )(
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
          )
        )
      )
    }

    def content: Modifier = {
      tabContentContainer
    }

    def render(md: ModifierT*): ModifierT = {
      Seq(navbar, contentContainer(content)) ++ md
    }
  }

  // noinspection TypeAnnotation
  case class NavigationBarBuilder(
      navTabs: NavigationTabs,
      navId: String,
      brand: Modifier,
      styles: Seq[NavigationBarStyle],
      container: Modifier ⇒ Modifier,
      contentContainer: Modifier ⇒ Modifier
  ) extends AbstractNavigationBar
      with BootstrapComponent {

    def withTabs(tabs: NavigationTabs)                              = copy(navTabs = tabs)
    def withTabs(tabs: NavigationTab*)                              = copy(navTabs = NavigationTabs.fromSeq(tabs))
    def withId(id: String)                                          = copy(navId = id)
    def withBrand(brand: Modifier*)                                 = copy(brand = brand)
    def withStyles(styles: NavigationBarStyle*)                     = copy(styles = styles)
    def withContainer(container: Modifier ⇒ Modifier)               = copy(container = container)
    def withContentContainer(contentContainer: Modifier ⇒ Modifier) = copy(contentContainer = contentContainer)

    def build(): UniversalNavigationBar = {
      new UniversalNavigationBar(navTabs, navId, brand, styles, container, contentContainer)
    }

    def render(md: ModifierT*): ModifierT = {
      build().render(md: _*)
    }
  }
}
