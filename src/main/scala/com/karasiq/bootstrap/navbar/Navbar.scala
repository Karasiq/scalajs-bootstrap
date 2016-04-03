package com.karasiq.bootstrap.navbar

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.grid.GridSystem
import com.karasiq.bootstrap.icons.IconModifier.NoIcon
import com.karasiq.bootstrap.navbar.Navigation.Tab
import com.karasiq.bootstrap.{Bootstrap, BootstrapComponent}
import org.scalajs.jquery.jQuery
import rx._

import scalatags.JsDom.all._

/**
  * Simple bootstrap navigation bar
  */
final class Navbar(barId: String, brand: Modifier, styles: Seq[NavbarStyle], container: Modifier ⇒ Modifier, contentContainer: Modifier ⇒ Modifier)(implicit ctx: Ctx.Owner) extends BootstrapComponent {
  import com.karasiq.bootstrap.BootstrapAttrs._
  private val nav = "nav".tag

  val tabs: Var[Seq[Tab]] = Var(Nil)

  private val tabContainer = Rx {
    def renderTab(active: Boolean, tab: Tab): Tag = {
      val idLink = s"$barId-${tab.id}-tab"
      li(
        tab.modifiers,
        "nav-item".addClass,
        "active".classIf(active),
        a("nav-link".addClass, href := "#", aria.controls := idLink, role := "tab", `data-toggle` := "tab", `data-target` := s"#$idLink")(
          if (tab.icon != NoIcon) Seq[Modifier](tab.icon, raw("&nbsp;")) else (),
          tab.name
        )
      )
    }

    val tabs = this.tabs()
    ul("nav".addClass, "navbar-nav".addClass)(
      renderTab(active = true, tabs.head),
      for (tab <- tabs.tail) yield renderTab(active = false, tab)
    )
  }

  private val tabContentContainer = Rx {
    def renderContent(active: Boolean, tab: Tab): Tag = {
      div(id := s"$barId-${tab.id}-tab", role := "tabpanel", `class` := (if (active) "tab-pane active fade in" else "tab-pane fade"))(
        tab.content
      )
    }

    val tabs = this.tabs()
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
    jQuery(s"a[data-target='#$barId-$id-tab']").tab("show")
  }

  /**
    * Selects tab by index
    * @param i Tab index, starting from `0`
    */
  def selectTab(i: Int): Unit = {
    val tabs = this.tabs.now
    require(i >= 0 && tabs.length > i, s"Invalid tab index: $i")
    this.selectTab(tabs(i).id)
  }

  /**
    * Appends provided tabs to tab list
    * @param tabs Navbar tabs
    */
  def addTabs(tabs: Tab*): Unit = {
    this.tabs.update(this.tabs.now ++ tabs)
  }

  /**
    * Updates tab list
    * @param tabs Navbar tabs
    */
  def setTabs(tabs: Tab*): Unit = {
    this.tabs.update(tabs)
  }

  def navbar: Tag = {
    nav("navbar".addClass, styles)(container(Seq[Modifier](
      button(`class` := "navbar-toggler hidden-sm-up", `type` := "button", `data-toggle` := "collapse", `data-target` := s"#$barId", raw("&#9776;")),
      a(href := "#", `class` := "navbar-brand", brand),
      div(`class` := "collapse navbar-toggleable-xs", id := barId)(tabContainer)
    )))
  }

  def content: Modifier = {
    tabContentContainer
  }

  def render(md: Modifier*) = {
    Seq(navbar, contentContainer(content))
  }
}

object Navbar {
  def apply(tabs: Seq[Tab] = Nil, barId: String = Bootstrap.newId, brand: Modifier = "Navigation", styles: Seq[NavbarStyle] = Seq(NavbarStyle.light, NavbarStyle.fixedTop, NavbarBackground.primary), container: Modifier ⇒ Modifier = md ⇒ GridSystem.container(md), contentContainer: Modifier ⇒ Modifier = md ⇒ GridSystem.container(GridSystem.mkRow(md)))(implicit ctx: Ctx.Owner) = {
    NavbarBuilder(tabs, barId, brand, styles, container, contentContainer)
  }
}