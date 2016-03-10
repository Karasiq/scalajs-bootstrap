package com.karasiq.bootstrap.navbar

import com.karasiq.bootstrap.BootstrapImplicits._
import rx._

import scalatags.JsDom.all._

case class NavigationBarBuilder(tabs: Seq[NavigationTab], barId: String, brand: Modifier, styles: Seq[NavigationBarStyle], container: Tag, contentContainer: Tag) {
  def withTabs(tabs: NavigationTab*) = copy(tabs = tabs)
  def withId(id: String) = copy(barId = id)
  def withBrand(brand: Modifier*) = copy(brand = brand)
  def withStyles(styles: NavigationBarStyle*) = copy(styles = styles)
  def withContainer(container: Tag) = copy(container = container)
  def withContentContainer(contentContainer: Tag) = copy(contentContainer = contentContainer)

  def build()(implicit ctx: Ctx.Owner) = {
    val bar = new NavigationBar(barId, brand, styles, container, contentContainer)
    bar.addTabs(tabs:_*)
    bar
  }
}