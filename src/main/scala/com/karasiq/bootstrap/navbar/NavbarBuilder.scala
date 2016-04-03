package com.karasiq.bootstrap.navbar

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.navbar.Navigation.Tab
import rx._

import scalatags.JsDom.all._

case class NavbarBuilder(tabs: Seq[Tab], barId: String, brand: Modifier, styles: Seq[NavbarStyle], container: Modifier ⇒ Modifier, contentContainer: Modifier ⇒ Modifier) {
  def withTabs(tabs: Tab*) = copy(tabs = tabs)
  def withId(id: String) = copy(barId = id)
  def withBrand(brand: Modifier*) = copy(brand = brand)
  def withStyles(styles: NavbarStyle*) = copy(styles = styles)
  def withContainer(container: Modifier ⇒ Modifier) = copy(container = container)
  def withContentContainer(contentContainer: Modifier ⇒ Modifier) = copy(contentContainer = contentContainer)

  def build()(implicit ctx: Ctx.Owner) = {
    val bar = new Navbar(barId, brand, styles, container, contentContainer)
    bar.addTabs(tabs:_*)
    bar
  }
}