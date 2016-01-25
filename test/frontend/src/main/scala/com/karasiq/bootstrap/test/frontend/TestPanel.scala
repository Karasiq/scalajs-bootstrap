package com.karasiq.bootstrap.test.frontend

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.buttons._
import com.karasiq.bootstrap.collapse.Collapse
import com.karasiq.bootstrap.dropdown.Dropdown
import com.karasiq.bootstrap.grid.GridSystem
import com.karasiq.bootstrap.navbar.{Navigation, NavigationTab}
import com.karasiq.bootstrap.panel.{Panel, PanelStyle}
import com.karasiq.bootstrap.progressbar.ProgressBarStyle
import com.karasiq.bootstrap.{Bootstrap, BootstrapHtmlComponent}
import org.scalajs.dom
import rx._

import scala.concurrent.duration._
import scala.language.postfixOps
import scalatags.JsDom.all._

final class TestPanel(panelTitle: String, style: PanelStyle) extends BootstrapHtmlComponent[dom.html.Div] {
  override def renderTag(md: Modifier*): RenderedTag = {
    val successButton = Button(ButtonStyle.success)("Win 10000000$", onclick := Bootstrap.jsClick(_ ⇒ TestModal().show())).render
    val dangerButton = Button(ButtonStyle.danger)("Format C:\\", onclick := Bootstrap.jsClick(_ ⇒ dom.alert("Boom"))).render

    val toggleButton = Bootstrap.button("Toggle me").toggleButton
    val disabledButton = Bootstrap.button("Heavy computation").disabledButton

    Obs(disabledButton.state, "disabled-button-click-handler") {
      if (disabledButton.state.now) {
        dom.setTimeout(() ⇒ {
          dom.alert(s"Answer: ${if (toggleButton.state.now) 321 else 123}")
          disabledButton.state.update(false)
        }, 1000)
      }
    }

    // Render panel
    import Panel._
    val panelId = Bootstrap.newId
    Panel(panelId, style)
      .withHeader(title("euro", collapse(panelId, panelTitle, raw("&nbsp"), Bootstrap.badge("42")), buttons(
        button("plus", onclick := Bootstrap.jsClick(_ ⇒ dom.alert("Panel add"))),
        button("minus", onclick := Bootstrap.jsClick(_ ⇒ dom.alert("Panel remove")))
      )))
      .renderTag(
        new TestProgressBar(ProgressBarStyle.success, 300 millis),
        Navigation.tabs(
          NavigationTab("Simple buttons", Bootstrap.newId, "remove", Bootstrap.well(
            GridSystem.mkRow(
              ButtonGroup(ButtonGroupSize.default, successButton, dangerButton)
            ),
            GridSystem.mkRow(Collapse("Dropdowns")(
              GridSystem.row(
                GridSystem.col(6).asDiv(Dropdown("Dropdown", Dropdown.item("Test 1", onclick := Bootstrap.jsClick(_ ⇒ dom.alert("Test 1"))), Dropdown.item("Test 2"))),
                GridSystem.col(6).asDiv(Dropdown.dropup("Dropup", Dropdown.item("Test 3", onclick := Bootstrap.jsClick(_ ⇒ dom.alert("Test 3"))), Dropdown.item("Test 4")))
              )
            ))
          )),
          NavigationTab("Reactive buttons", Bootstrap.newId, "play-circle", Bootstrap.well(
            ButtonGroup(ButtonGroupSize.large, toggleButton, disabledButton)
          ))
        )
      )
  }
}
