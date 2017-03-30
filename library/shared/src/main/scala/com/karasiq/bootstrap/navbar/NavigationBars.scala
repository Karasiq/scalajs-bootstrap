package com.karasiq.bootstrap.navbar

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap.grid.Grids
import com.karasiq.bootstrap.icons.Icons
import com.karasiq.bootstrap.utils.{ClassModifiers, Utils}
import rx.Rx

import scala.language.{implicitConversions, postfixOps}

trait NavigationBars { self: RenderingContext with Icons with Grids with Utils with BootstrapComponents with ClassModifiers ⇒
  import BootstrapAttrs._

  import scalaTags.all._

  case class NavigationTab(name: Modifier, id: String, icon: IconModifier, content: Modifier, modifiers: Modifier*)
  case class NavigationTabs(tabs: Rx[Seq[NavigationTab]])

  object NavigationTabs {
    implicit def toRxSeq(nt: NavigationTabs): Rx[Seq[NavigationTab]] = nt.tabs
    implicit def fromRxSeq(seq: Rx[Seq[NavigationTab]]): NavigationTabs = new NavigationTabs(seq)
    implicit def fromSeq(seq: Seq[NavigationTab]): NavigationTabs = fromRxSeq(Rx(seq))
  }

  trait NavComponent {
    val navId: String
    val navTabs: NavigationTabs
  }

  class Navigation(val navTabs: NavigationTabs, val navType: String = "tabs",
                   val navId: String = Bootstrap.newId) extends NavComponent with BootstrapHtmlComponent {
    private def tabContainer = Rx {
      def renderTab(tab: NavigationTab): Tag = {
        val idLink = s"$navId-${tab.id}-tab"
        li(role := "presentation", tab.modifiers)(
          a(href := "#", aria.controls := idLink, role := "tab", `data-toggle` := "tab", `data-target` := s"#$idLink")(
            if (tab.icon != NoIcon) Seq[Modifier](tab.icon, raw("&nbsp;")) else (),
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
  class NavigationBar(val navTabs: NavigationTabs,
                      brand: Modifier,
                      styles: Seq[NavigationBarStyle],
                      container: Modifier ⇒ Modifier,
                      contentContainer: Modifier ⇒ Modifier,
                      val navId: String = Bootstrap.newId) extends NavComponent with BootstrapComponent {

    private[this] val nav = tag("nav")

    private[this] val tabContainer = Rx {
      def renderTab(active: Boolean, tab: NavigationTab): Tag = {
        val idLink = s"$navId-${tab.id}-tab"
        li(
          tab.modifiers,
          "active".classIf(active),
          a(href := "#", aria.controls := idLink, role := "tab", `data-toggle` := "tab", `data-target` := s"#$idLink")(
            if (tab.icon != NoIcon) Seq[Modifier](tab.icon, raw("&nbsp;")) else (),
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
      Seq(navbar, contentContainer(content))
    }
  }

  //noinspection TypeAnnotation
  object Navigation {
    def tabs(tabs: NavigationTab*) = {
      new Navigation(Rx(tabs))
    }

    def pills(tabs: NavigationTab*) = {
      new Navigation(Rx(tabs), "pills")
    }
  }

  object NavigationBar {
    def apply(tabs: Seq[NavigationTab] = Nil, barId: String = Bootstrap.newId, brand: Modifier = "Navigation", styles: Seq[NavigationBarStyle] = Seq(NavigationBarStyle.default, NavigationBarStyle.fixedTop), container: Modifier ⇒ Modifier = md ⇒ GridSystem.container(md), contentContainer: Modifier ⇒ Modifier = md ⇒ GridSystem.container(GridSystem.mkRow(md))): NavigationBarBuilder = {
      NavigationBarBuilder(tabs, barId, brand, styles, container, contentContainer)
    }
  }

  //noinspection TypeAnnotation
  case class NavigationBarBuilder(tabs: NavigationTabs, navId: String, brand: Modifier, styles: Seq[NavigationBarStyle], container: Modifier ⇒ Modifier, contentContainer: Modifier ⇒ Modifier) {
    def withTabs(tabs: NavigationTabs) = copy(tabs = tabs)
    def withTabs(tabs: NavigationTab*) = copy(tabs = NavigationTabs.fromSeq(tabs))
    def withId(id: String) = copy(navId = id)
    def withBrand(brand: Modifier*) = copy(brand = brand)
    def withStyles(styles: NavigationBarStyle*) = copy(styles = styles)
    def withContainer(container: Modifier ⇒ Modifier) = copy(container = container)
    def withContentContainer(contentContainer: Modifier ⇒ Modifier) = copy(contentContainer = contentContainer)

    def build() = {
      new NavigationBar(tabs, brand, styles, container, contentContainer, navId)
    }
  }

  final class NavigationBarStyle private[navbar](style: String) extends ModifierFactory {
    val className = s"navbar-$style"
    val createModifier = className.addClass
  }

  object NavigationBarStyle {
    // Style
    lazy val default = new NavigationBarStyle("default")
    lazy val inverse = new NavigationBarStyle("inverse")

    // Position
    lazy val fixedTop = new NavigationBarStyle("fixed-top")
    lazy val fixedBottom = new NavigationBarStyle("fixed-bottom")
    lazy val staticTop = new NavigationBarStyle("static-top")
  }
}

