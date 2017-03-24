package com.karasiq.bootstrap_text.navbar

import com.karasiq.bootstrap_text.BootstrapAttrs.{`data-target`, `data-toggle`}
import com.karasiq.bootstrap_text.BootstrapImplicits._
import com.karasiq.bootstrap_text.grid.GridSystem
import com.karasiq.bootstrap_text.icons.IconModifier.NoIcon
import com.karasiq.bootstrap_text.{Bootstrap, BootstrapComponent}

import scalatags.Text.all._

/**
  * Simple bootstrap navigation bar
  */
final class NavigationBar(barId: String, brand: Modifier, styles: Seq[NavigationBarStyle],
                          container: Modifier ⇒ Modifier, contentContainer: Modifier ⇒ Modifier,
                          tabs: Seq[NavigationTab]) extends BootstrapComponent {

  private val nav = tag("nav")

  private def tabContainer = {
    def renderTab(active: Boolean, tab: NavigationTab): Tag = {
      val idLink = s"$barId-${tab.id}-tab"
      li(
        tab.modifiers,
        "active".classIf(active),
        a(href := "#", aria.controls := idLink, role := "tab", `data-toggle` := "tab", `data-target` := s"#$idLink")(
          if (tab.icon != NoIcon) Seq[Modifier](tab.icon, raw("&nbsp;")) else (),
          tab.name
        )
      )
    }

    ul(`class` := "nav navbar-nav")(
      if (tabs.nonEmpty) Seq[Modifier](renderTab(active = true, tabs.head),
        for (tab <- tabs.tail) yield renderTab(active = false, tab)) else ()
    )
  }

  private def tabContentContainer = {
    def renderContent(active: Boolean, tab: NavigationTab): Tag = {
      div(id := s"$barId-${tab.id}-tab", role := "tabpanel", `class` := (if (active) "tab-pane active fade in" else "tab-pane fade"))(
        tab.content
      )
    }

    div(id := s"$barId-tabcontent", `class` := "tab-content")(
      if (tabs.nonEmpty) Seq[Modifier](renderContent(active = true, tabs.head),
        for (tab <- tabs.tail) yield renderContent(active = false, tab)) else ()
    )
  }

  def navbar: Tag = {
    nav("navbar".addClass, styles)(
      container(Seq(
        // Header
        div(`class` := "navbar-header")(
          button(`type` := "button", `data-toggle` := "collapse", `data-target` := s"#$barId", `class` := "navbar-toggle collapsed")(
            span(`class` := "sr-only", "Toggle navigation"),
            span(`class` := "icon-bar"),
            span(`class` := "icon-bar"),
            span(`class` := "icon-bar")
          ),
          a(href := "#", `class` := "navbar-brand", brand)
        ),
        div(id := barId, `class` := "navbar-collapse collapse")(
          tabContainer
        )
      ))
    )
  }

  def content: Tag = {
    tabContentContainer
  }

  def render(md: Modifier*): Modifier = {
    Seq(navbar, contentContainer(content(md:_*)))
  }
}

object NavigationBar {
  def apply(tabs: Seq[NavigationTab] = Nil, barId: String = Bootstrap.newId, brand: Modifier = "Navigation", styles: Seq[NavigationBarStyle] = Seq(NavigationBarStyle.default, NavigationBarStyle.fixedTop), container: Modifier ⇒ Modifier = md ⇒ GridSystem.container(md), contentContainer: Modifier ⇒ Modifier = md ⇒ GridSystem.container(GridSystem.mkRow(md))): NavigationBarBuilder = {
    NavigationBarBuilder(tabs, barId, brand, styles, container, contentContainer)
  }
}
