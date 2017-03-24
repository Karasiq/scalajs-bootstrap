package com.karasiq.bootstrap_text.navbar

import com.karasiq.bootstrap_text.BootstrapAttrs._
import com.karasiq.bootstrap_text.BootstrapImplicits._
import com.karasiq.bootstrap_text.icons.IconModifier.NoIcon
import com.karasiq.bootstrap_text.{Bootstrap, BootstrapHtmlComponent}

import scalatags.Text.all._

abstract class Navigation extends BootstrapHtmlComponent {
  def navId: String

  def navType: String

  def content: Seq[NavigationTab]

  private def tabContainer = {
    def renderTab(tab: NavigationTab): Tag = {
      val idLink = s"$navId-${tab.id}-tab"
      li(role := "presentation", tab.modifiers)(
        a(href := "#", aria.controls := idLink, role := "tab", `data-toggle` := "tab", `data-target` := s"#$idLink")(
          if (tab.icon != NoIcon) Seq[Modifier](tab.icon, raw("&nbsp;")) else (),
          tab.name
        )
      )
    }

    val tabs = content
    ul(`class` := s"nav nav-$navType", role := "tablist")(
      renderTab(tabs.head)("active".addClass),
      for (t <- tabs.tail) yield renderTab(t)
    )
  }

  private def tabContentContainer = {
    def renderPanel(t: NavigationTab): Tag = {
      div(role := "tabpanel", "tab-pane".addClass, "fade".addClass, id := s"$navId-${t.id}-tab")(
        t.content
      )
    }

    val tabs = content
    div("tab-content".addClass)(
      renderPanel(tabs.head)("active".addClass, "in".addClass),
      for (t <- tabs.tail) yield renderPanel(t)
    )
  }

  override def renderTag(md: Modifier*): RenderedTag = {
    div(tabContainer, tabContentContainer, md)
  }
}

object Navigation {
  def tabs(tabs: NavigationTab*): Navigation = {
    new Navigation {
      override val navId: String = Bootstrap.newId
      override val navType: String = "tabs"
      override val content: Seq[NavigationTab] = tabs
    }
  }

  def pills(tabs: NavigationTab*): Navigation = {
    new Navigation {
      override val navId: String = Bootstrap.newId
      override val navType: String = "pills"
      override val content: Seq[NavigationTab] = tabs
    }
  }
}
