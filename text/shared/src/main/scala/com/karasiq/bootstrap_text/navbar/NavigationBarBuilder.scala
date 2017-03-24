package com.karasiq.bootstrap_text.navbar

import scalatags.Text.all._

//noinspection TypeAnnotation
case class NavigationBarBuilder(tabs: Seq[NavigationTab], barId: String, brand: Modifier, styles: Seq[NavigationBarStyle], container: Modifier ⇒ Modifier, contentContainer: Modifier ⇒ Modifier) {
  def withTabs(tabs: NavigationTab*) = copy(tabs = tabs)
  def withId(id: String) = copy(barId = id)
  def withBrand(brand: Modifier*) = copy(brand = brand)
  def withStyles(styles: NavigationBarStyle*) = copy(styles = styles)
  def withContainer(container: Modifier ⇒ Modifier) = copy(container = container)
  def withContentContainer(contentContainer: Modifier ⇒ Modifier) = copy(contentContainer = contentContainer)

  def build(): NavigationBar = {
    new NavigationBar(barId, brand, styles, container, contentContainer, tabs)
  }
}