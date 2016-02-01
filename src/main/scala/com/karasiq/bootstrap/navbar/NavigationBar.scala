package com.karasiq.bootstrap.navbar

import com.karasiq.bootstrap.Bootstrap
import com.karasiq.bootstrap.BootstrapImplicits._
import org.scalajs.jquery.jQuery
import rx._

import scalatags.JsDom.all._

/**
  * Simple bootstrap navigation bar
  * @param barId Navbar id
  */
final class NavigationBar(barId: String = Bootstrap.newId)(implicit ctx: Ctx.Owner) {
  private val nav = "nav".tag
  private val `data-toggle` = "data-toggle".attr
  private val `data-target` = "data-target".attr

  val navigationTabs: Var[Seq[NavigationTab]] = Var(Nil)

  private val tabContainer = Rx {
    def renderTab(active: Boolean, tab: NavigationTab): Tag = {
      li(
        tab.modifiers,
        "active".classIf(active),
        a(href := s"#$barId-${tab.id}-tab", role := "tab", `data-toggle` := "tab")(
          span(`class` := s"glyphicon glyphicon-${tab.icon}"),
          raw("&nbsp;"),
          tab.name
        )
      )
    }

    val tabs = navigationTabs()
    ul(`class` := "nav navbar-nav")(
      renderTab(active = true, tabs.head),
      for (tab <- tabs.tail) yield renderTab(active = false, tab)
    )
  }

  private val tabContentContainer = Rx {
    def renderContent(active: Boolean, tab: NavigationTab): Tag = {
      div(id := s"$barId-${tab.id}-tab", role := "tabpanel", `class` := (if (active) "tab-pane active fade in" else "tab-pane fade"))(
        tab.content
      )
    }

    val tabs = navigationTabs()
    div(id := s"$barId-tabcontent", `class` := "tab-content")(
      renderContent(active = true, tabs.head),
      for (tab <- tabs.tail) yield renderContent(active = false, tab)
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
    val tabs = navigationTabs.now
    require(i >= 0 && tabs.length > i, s"Invalid tab index: $i")
    this.selectTab(tabs(i).id)
  }

  /**
    * Appends provided tabs to tab list
    * @param tabs Navbar tabs
    */
  def addTabs(tabs: NavigationTab*): Unit = {
    navigationTabs.update(navigationTabs.now ++ tabs)
  }

  /**
    * Updates tab list
    * @param tabs Navbar tabs
    */
  def setTabs(tabs: NavigationTab*): Unit = {
    navigationTabs.update(tabs)
  }

  def navbar(brand: Modifier): Tag = {
    nav(`class` := "navbar navbar-default navbar-fixed-top")(
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

object NavigationBar {
  def apply(tabs: NavigationTab*)(implicit ctx: Ctx.Owner): NavigationBar = {
    val navbar = new NavigationBar()
    navbar.addTabs(tabs:_*)
    navbar
  }
}