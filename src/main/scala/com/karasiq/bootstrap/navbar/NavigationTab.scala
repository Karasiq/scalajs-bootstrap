package com.karasiq.bootstrap.navbar

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.icons.IconModifier

import scalatags.JsDom.all._

case class NavigationTab(name: Modifier, id: String, icon: IconModifier, content: Modifier, modifiers: Modifier*)
