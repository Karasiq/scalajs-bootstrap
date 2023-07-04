package com.karasiq.bootstrap

import scala.language.postfixOps

import com.karasiq.bootstrap.alert.{Alerts, UniversalAlerts}
import com.karasiq.bootstrap.buttons.{Buttons, UniversalButtons}
import com.karasiq.bootstrap.carousel.{Carousels, UniversalCarousels}
import com.karasiq.bootstrap.collapse.{Collapses, UniversalCollapses}
import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.{ClassModifiers, RenderingContext}
import com.karasiq.bootstrap.dropdown.{Dropdowns, UniversalDropdowns}
import com.karasiq.bootstrap.form.{Forms, UniversalForms}
import com.karasiq.bootstrap.grid.{Grids, UniversalGrids}
import com.karasiq.bootstrap.icons.{Icons, UniversalIcons}
import com.karasiq.bootstrap.modal.{Modals, UniversalModals}
import com.karasiq.bootstrap.navbar.{NavigationBars, UniversalNavigationBars}
import com.karasiq.bootstrap.pagination.{PageSelectors, UniversalPageSelectors}
import com.karasiq.bootstrap.panel.{Panels, UniversalPanels}
import com.karasiq.bootstrap.popover.Popovers
import com.karasiq.bootstrap.progressbar.{ProgressBars, UniversalProgressBars}
import com.karasiq.bootstrap.table._
import com.karasiq.bootstrap.tooltip.Tooltips
import com.karasiq.bootstrap.utils.{UniversalUtils, Utils}

// Abstract components
trait BootstrapBundle
    extends RenderingContext
    with BootstrapComponents
    with ClassModifiers
    with Alerts
    with Buttons
    with Carousels
    with Collapses
    with Dropdowns
    with Forms
    with Grids
    with Icons
    with Modals
    with NavigationBars
    with Panels
    with Popovers
    with ProgressBars
    with Tables
    with PageSelectors
    with PagedTables
    with SortableTables
    with Tooltips
    with Utils

// Default components implementation
trait UniversalBootstrapBundle
    extends BootstrapBundle
    with UniversalPageSelectors
    with UniversalTables
    with UniversalPagedTables
    with UniversalSortableTables
    with UniversalProgressBars
    with UniversalPanels
    with UniversalNavigationBars
    with UniversalModals
    with UniversalIcons
    with UniversalGrids
    with UniversalForms
    with UniversalDropdowns
    with UniversalCollapses
    with UniversalCarousels
    with UniversalButtons
    with UniversalAlerts
    with UniversalUtils
