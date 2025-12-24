package com.karasiq.bootstrap4.navbar

import rx.Rx

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.{ClassModifiers, RenderingContext}
import com.karasiq.bootstrap4.grid.Grids
import com.karasiq.bootstrap4.icons.Icons
import com.karasiq.bootstrap4.utils.Utils

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
        styles: Seq[Modifier] = Seq(
          NavigationBarStyle.light,
          NavigationBarStyle.fixedTop,
          NavigationBarExpand.lg,
          Bootstrap.background.light
        ),
        inlineContent: Modifier = (),
        container: Modifier ⇒ Modifier = md ⇒ GridSystem.container(md),
        contentContainer: Modifier ⇒ Modifier = md ⇒ GridSystem.container(GridSystem.mkRow(md))
    ): NavigationBarBuilder = {
      NavigationBarBuilder(tabs, barId, brand, styles, inlineContent, container, contentContainer)
    }
  }

  class UniversalNavigation(
      val navTabs: NavigationTabs,
      val navType: String = "tabs",
      val navId: String = Bootstrap.newId
  ) extends AbstractNavigation
      with BootstrapHtmlComponent {

    private def tabContainer = Rx {
      def renderTab(tab: NavigationTab, active: Boolean): Tag = {
        val idLink = this.tabId(tab.id)
        li(`class` := "nav-item", tab.modifiers)(
          a(
            `class` := "nav-link",
            "active".classIf(active),
            href          := "#",
            aria.controls := idLink,
            role          := "tab",
            `data-toggle` := "tab",
            `data-target` := s"#$idLink"
          )(
            if (tab.icon != NoIcon) Seq[Modifier](tab.icon, Bootstrap.nbsp) else (),
            tab.name
          )
        )
      }

      val tabs = navTabs()
      val tag  = ul(`class` := "nav nav-" + navType, role := "tablist")

      if (tabs.nonEmpty)
        tag(
          renderTab(tabs.head, active = true),
          for (t ← tabs.tail) yield renderTab(t, active = false)
        )
      else tag
    }

    private def tabContentContainer = Rx {
      def renderPanel(t: NavigationTab): Tag = {
        div(role := "tabpanel", "tab-pane".addClass, "fade".addClass, id := this.tabId(t.id))(
          t.content
        )
      }

      val tabs = navTabs()
      div("tab-content".addClass)(
        renderPanel(tabs.head)("active".addClass, "show".addClass),
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
      styles: Seq[Modifier],
      inlineContent: Modifier,
      container: Modifier ⇒ Modifier,
      contentContainer: Modifier ⇒ Modifier
  ) extends AbstractNavigationBar
      with BootstrapComponent {

    private[this] val nav = tag("nav")

    private[this] val tabContainer = Rx {
      def renderTab(active: Boolean, tab: NavigationTab): Tag = {
        val idLink = this.tabId(tab.id)
        li(
          `class` := "nav-item",
          a(
            href    := "#",
            `class` := "nav-link",
            "active".classIf(active),
            aria.controls := idLink,
            role          := "tab",
            `data-toggle` := "tab",
            `data-target` := s"#$idLink",
            id            := idLink + "link"
          )(
            if (tab.icon != NoIcon) Seq[Modifier](tab.icon, Bootstrap.nbsp) else (),
            tab.name
          ),
          tab.modifiers
        )
      }

      val tabs = navTabs()
      val tag  = ul(`class` := "navbar-nav nav mr-auto", role := "tablist")
      if (tabs.nonEmpty)
        tag(
          renderTab(active = true, tabs.head),
          for (tab ← tabs.tail) yield renderTab(active = false, tab)
        )
      else tag
    }

    private[this] val tabContentContainer = Rx {
      def renderContent(active: Boolean, tab: NavigationTab): Tag = {
        val tabId = this.tabId(tab.id)
        div(
          id              := tabId,
          role            := "tabpanel",
          `class`         := (if (active) "tab-pane active fade show" else "tab-pane fade"),
          aria.labelledby := tabId + "link"
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

    def navbar(md: Modifier*): Tag = {
      nav(`class` := "navbar", styles)(
        a(href := "javascript:void(0);", `class` := "navbar-brand", brand),
        button(
          `class`       := "navbar-toggler",
          `type`        := "button",
          `data-toggle` := "collapse",
          `data-target` := "#" + navId,
          aria.controls := navId,
          aria.expanded := false,
          aria.label    := "Toggle navigation",
          span(`class` := "navbar-toggler-icon")
        ),
        div(`class` := "collapse navbar-collapse", id := navId, tabContainer, inlineContent, md)
      )
    }

    def content: Modifier = {
      tabContentContainer
    }

    def render(md: ModifierT*): ModifierT = {
      Seq(container(navbar(md: _*)), contentContainer(content))
    }
  }

  // noinspection TypeAnnotation
  case class NavigationBarBuilder(
      navTabs: NavigationTabs,
      navId: String,
      brand: Modifier,
      styles: Seq[Modifier],
      inlineContent: Modifier,
      container: Modifier ⇒ Modifier,
      contentContainer: Modifier ⇒ Modifier
  ) extends AbstractNavigationBar
      with BootstrapComponent {

    def withTabs(tabs: NavigationTabs)                              = copy(navTabs = tabs)
    def withTabs(tabs: NavigationTab*)                              = copy(navTabs = NavigationTabs.fromSeq(tabs))
    def withId(id: String)                                          = copy(navId = id)
    def withBrand(brand: Modifier*)                                 = copy(brand = brand)
    def withStyles(styles: Modifier*)                               = copy(styles = styles)
    def withInlineContent(inlineContent: Modifier)                  = copy(inlineContent = inlineContent)
    def withContainer(container: Modifier ⇒ Modifier)               = copy(container = container)
    def withContentContainer(contentContainer: Modifier ⇒ Modifier) = copy(contentContainer = contentContainer)

    def build(): UniversalNavigationBar = {
      new UniversalNavigationBar(navTabs, navId, brand, styles, inlineContent, container, contentContainer)
    }

    def render(md: ModifierT*): ModifierT = {
      build().render(md: _*)
    }
  }
}
