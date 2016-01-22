package com.karasiq.bootstrap.navbar

import org.scalajs.dom

import scalatags.JsDom.all._

case class NavigationTab(name: String, id: String, icon: String, content: dom.Element, active: Boolean = false)

/**
  * Simple bootstrap navigation bar
  * @param barId Navbar id
  * @param tabs Navbar tabs
  */
final class NavigationBar(barId: String, tabs: NavigationTab*) {
  private val nav = "nav".tag
  private val `data-toggle` = "data-toggle".attr
  private val `data-target` = "data-target".attr

  def navbar(brand: String, classes: Seq[String] = Seq("navbar-fixed-top")): Tag = {
    def renderTab(tab: NavigationTab): Tag = {
      li(
        `class` := (if (tab.active) "active" else ""),
        a(href := s"#$barId-${tab.id}-tab", role := "tab", `data-toggle` := "tab")(
          span(`class` := s"glyphicon glyphicon-${tab.icon}"),
          raw("&nbsp;"),
          tab.name
        )
      )
    }

    nav(`class` := (Seq("navbar", "navbar-default") ++ classes).mkString(" "))(
      div(`class` := "container")(
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
          ul(`class` := "nav navbar-nav")(
            tabs.map(renderTab)
          )
        )
      )
    )
  }

  def content: Tag = {
    div(id := s"$barId-tabcontent", `class` := "tab-content")(
      for (NavigationTab(name, tabId, _, content, active) <- tabs) yield {
        div(id := s"$barId-$tabId-tab", role := "tabpanel", `class` := (if (active) "tab-pane active fade in" else "tab-pane fade"))(
          content
        )
      }
    )
  }
}
