package com.karasiq.bootstrap4.context

import scala.language.postfixOps

import com.karasiq.bootstrap4.alert.{Alerts, UniversalAlerts}
import com.karasiq.bootstrap4.buttons.{Buttons, UniversalButtons}
import com.karasiq.bootstrap4.card.{Cards, UniversalCards}
import com.karasiq.bootstrap4.carousel.{Carousels, UniversalCarousels}
import com.karasiq.bootstrap4.collapse.{Collapses, UniversalCollapses}
import com.karasiq.bootstrap4.components.BootstrapComponents
import com.karasiq.bootstrap4.dropdown.{Dropdowns, UniversalDropdowns}
import com.karasiq.bootstrap4.form.{Forms, UniversalForms}
import com.karasiq.bootstrap4.grid.{Grids, UniversalGrids}
import com.karasiq.bootstrap4.icons.{Icons, UniversalIcons}
import com.karasiq.bootstrap4.modal.{Modals, UniversalModals}
import com.karasiq.bootstrap4.navbar.{NavigationBars, UniversalNavigationBars}
import com.karasiq.bootstrap4.pagination.{PageSelectors, UniversalPageSelectors}
import com.karasiq.bootstrap4.popover.Popovers
import com.karasiq.bootstrap4.progressbar.{ProgressBars, UniversalProgressBars}
import com.karasiq.bootstrap4.table.{PagedTables, Tables, UniversalPagedTables, UniversalTables}
import com.karasiq.bootstrap4.tooltip.Tooltips
import com.karasiq.bootstrap4.utils.{ClassModifiers, UniversalUtils, Utils}

// Abstract components
trait BootstrapBundle extends RenderingContext with BootstrapComponents with ClassModifiers with Alerts with Buttons
  with Carousels with Collapses with Dropdowns with Forms with Grids with Icons with Modals with NavigationBars
  with Cards with Popovers with ProgressBars with Tables with PageSelectors with PagedTables with Tooltips with Utils

// Default components implementation
trait UniversalBootstrapBundle extends BootstrapBundle
  with UniversalPageSelectors with UniversalTables with UniversalPagedTables with UniversalProgressBars with UniversalCards
  with UniversalNavigationBars with UniversalModals with UniversalIcons with UniversalGrids with UniversalForms with UniversalDropdowns
  with UniversalCollapses with UniversalCarousels with UniversalButtons with UniversalAlerts with UniversalUtils
