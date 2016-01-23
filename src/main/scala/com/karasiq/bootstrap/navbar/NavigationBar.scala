package com.karasiq.bootstrap.navbar

import com.karasiq.bootstrap.Bootstrap
import com.karasiq.bootstrap.BootstrapImplicits._
import org.scalajs.jquery.jQuery
import rx._

import scalatags.JsDom.all._

case class NavigationTab(name: String, id: String, icon: String, content: Modifier, active: Boolean = false)

/**
  * Simple bootstrap navigation bar
  * @param barId Navbar id
  */
final class NavigationBar(barId: String = Bootstrap.newId) {
  private val nav = "nav".tag
  private val `data-toggle` = "data-toggle".attr
  private val `data-target` = "data-target".attr

  val navigationTabs: Var[Seq[NavigationTab]] = Var(Nil)

  private def renderTab(tab: NavigationTab): Tag = {
    li(
      `class` := (if (tab.active) "active" else ""),
      a(href := s"#$barId-${tab.id}-tab", role := "tab", `data-toggle` := "tab")(
        span(`class` := s"glyphicon glyphicon-${tab.icon}"),
        raw("&nbsp;"),
        tab.name
      )
    )
  }

  private val tabContainer = Rx {
    ul(`class` := "nav navbar-nav")(
      for (tab <- navigationTabs()) yield {
        renderTab(tab)
      }
    )
  }

  private val tabContentContainer = Rx {
    div(id := s"$barId-tabcontent", `class` := "tab-content")(
      for (NavigationTab(_, tabId, _, content, active) <- navigationTabs()) yield {
        div(id := s"$barId-$tabId-tab", role := "tabpanel", `class` := (if (active) "tab-pane active fade in" else "tab-pane fade"))(
          content
        )
      }
    )
  }

  /**
    * Selects tab by ID
    * @param id Tab ID
    */
  def selectTab(id: String): Unit = {
    jQuery(s"a[href='#$barId-$id-tab']").tab("show")
  }

  /**
    * Selects tab by index
    * @param i Tab index, starting from `0`
    */
  def selectTab(i: Int): Unit = {
    val tabs = navigationTabs()
    require(i >= 0 && tabs.length > i, s"Invalid tab index: $i")
    this.selectTab(tabs(i).id)
  }

  /**
    * Appends provided tabs to tab list
    * @param tabs Navbar tabs
    */
  def addTabs(tabs: NavigationTab*): Unit = {
    navigationTabs.update(navigationTabs() ++ tabs)
  }

  /**
    * Updates tab list
    * @param tabs Navbar tabs
    */
  def setTabs(tabs: NavigationTab*): Unit = {
    navigationTabs.update(tabs)
  }

  def navbar(brand: String, classes: Seq[String] = Seq("navbar-fixed-top")): Tag = {
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
          tabContainer
        )
      )
    )
  }

  def content: Modifier = {
    tabContentContainer
  }
}
