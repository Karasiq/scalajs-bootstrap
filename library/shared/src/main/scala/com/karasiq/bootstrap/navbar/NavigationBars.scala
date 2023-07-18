package com.karasiq.bootstrap.navbar

import scala.language.{implicitConversions, postfixOps}

import rx.Rx

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.{ClassModifiers, RenderingContext}
import com.karasiq.bootstrap.grid.Grids
import com.karasiq.bootstrap.icons.Icons
import com.karasiq.bootstrap.utils.Utils

trait NavigationBars extends NavigationBarStyles {
  self: RenderingContext with Icons with Grids with Utils with BootstrapComponents with ClassModifiers ⇒
  import scalaTags.all._

  // -----------------------------------------------------------------------
  // Tabs
  // -----------------------------------------------------------------------
  case class NavigationTab(name: Modifier, id: String, icon: IconModifier, content: Modifier, modifiers: Modifier*)
  case class NavigationTabs(tabs: Rx[Seq[NavigationTab]])

  object NavigationTabs {
    implicit def toRxSeq(nt: NavigationTabs): Rx[Seq[NavigationTab]]    = nt.tabs
    implicit def fromRxSeq(seq: Rx[Seq[NavigationTab]]): NavigationTabs = new NavigationTabs(seq)
    implicit def fromSeq(seq: Seq[NavigationTab]): NavigationTabs       = fromRxSeq(Rx(seq))
  }

  // -----------------------------------------------------------------------
  // Component definitions
  // -----------------------------------------------------------------------
  type Navigation <: AbstractNavigation with BootstrapComponent
  val Navigation: NavigationFactory

  val NavigationBar: NavigationBarFactory
  type NavigationBar <: AbstractNavigationBar with BootstrapComponent

  trait NavComponent {
    val navId: String
    val navTabs: NavigationTabs
    def tabId(id: String): String = s"$navId-$id-tab"
  }

  trait AbstractNavigation extends NavComponent {
    def navType: String
  }

  trait AbstractNavigationBar extends NavComponent

  trait NavigationFactory {
    def tabs(tabs: NavigationTab*): Navigation
    def pills(tabs: NavigationTab*): Navigation
  }

  trait NavigationBarFactory {

    /** Creates navigation bar
      * @param tabs
      *   Navbar tabs
      * @param barId
      *   Bar id attribute
      * @param brand
      *   Navbar "brand" content
      * @param styles
      *   Navbar styles
      * @param container
      *   Navbar container type
      * @param contentContainer
      *   Navbar content container type
      * @return
      *   Navigation bar
      */
    def apply(
        tabs: Seq[NavigationTab] = Nil,
        barId: String = Bootstrap.newId,
        brand: Modifier = "Navigation",
        styles: Seq[NavigationBarStyle] = Seq(NavigationBarStyle.default, NavigationBarStyle.fixedTop),
        container: Modifier ⇒ Modifier = md ⇒ GridSystem.container(md),
        contentContainer: Modifier ⇒ Modifier = md ⇒ GridSystem.container(GridSystem.mkRow(md))
    ): NavigationBar
  }
}
