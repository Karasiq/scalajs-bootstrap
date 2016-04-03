package com.karasiq.bootstrap.navbar

import com.karasiq.bootstrap.BootstrapAttrs._
import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.icons.IconModifier
import com.karasiq.bootstrap.icons.IconModifier.NoIcon
import com.karasiq.bootstrap.navbar.Navigation.Tab
import com.karasiq.bootstrap.{Bootstrap, BootstrapHtmlComponent}
import org.scalajs.dom
import org.scalajs.jquery.jQuery
import rx._

import scalatags.JsDom.all._

abstract class Navigation(implicit ctx: Ctx.Owner) extends BootstrapHtmlComponent[dom.html.Element] {
  def navId: String

  def navType: String

  def content: Rx[Seq[Tab]]

  private def tabContainer = Rx {
    def renderTab(tab: Tab): Tag = {
      val idLink = s"$navId-${tab.id}-tab"
      li("nav-item".addClass, role := "presentation", tab.modifiers)(
        a("nav-link".addClass, href := "#", aria.controls := idLink, role := "tab", `data-toggle` := "tab", `data-target` := s"#$idLink")(
          if (tab.icon != NoIcon) Seq[Modifier](tab.icon, raw("&nbsp;")) else (),
          tab.name
        )
      )
    }

    val tabs = content()
    ul(`class` := s"nav nav-$navType", role := "tablist")(
      renderTab(tabs.head)("active".addClass),
      for (t <- tabs.tail) yield renderTab(t)
    )
  }

  private def tabContentContainer = Rx {
    def renderPanel(t: Tab): Tag = {
      div(role := "tabpanel", "tab-pane".addClass, "fade".addClass, id := s"$navId-${t.id}-tab")(
        t.content
      )
    }

    val tabs = content()
    div("tab-content".addClass)(
      renderPanel(tabs.head)("active".addClass, "in".addClass),
      for (t <- tabs.tail) yield renderPanel(t)
    )
  }

  /**
    * Selects tab by ID
    * @param id Tab ID
    */
  def selectTab(id: String): Unit = {
    jQuery(s"a[data-target='#$navId-$id-tab']").tab("show")
  }

  /**
    * Selects tab by index
    * @param i Tab index, starting from `0`
    */
  def selectTab(i: Int): Unit = {
    val tabs = content.now
    require(i >= 0 && tabs.length > i, s"Invalid tab index: $i")
    this.selectTab(tabs(i).id)
  }

  override def renderTag(md: Modifier*): RenderedTag = {
    div(tabContainer, tabContentContainer, md)
  }
}

object Navigation {
  case class Tab(name: String, id: String, icon: IconModifier, content: Modifier, modifiers: Modifier*)

  def tabs(tabs: Tab*)(implicit ctx: Ctx.Owner): Navigation = {
    new Navigation {
      override val navId: String = Bootstrap.newId

      override val navType: String = "tabs"

      override val content: Rx[Seq[Tab]] = Rx(tabs)
    }
  }

  def pills(tabs: Tab*)(implicit ctx: Ctx.Owner): Navigation = {
    new Navigation {
      override val navId: String = Bootstrap.newId

      override val navType: String = "pills"

      override val content: Rx[Seq[Tab]] = Rx(tabs)
    }
  }
}
