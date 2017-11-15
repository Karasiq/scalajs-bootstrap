package com.karasiq.bootstrap4.test.frontend

import scala.concurrent.duration._
import scala.language.postfixOps

import org.scalajs.dom.window
import rx._

import com.karasiq.bootstrap4.Bootstrap.default._
import scalaTags.all.{span, _}

object TestPanel {
  def apply(panelTitle: String, style: CardStyle): TestPanel = {
    new TestPanel(panelTitle, style)
  }
}

final class TestPanel(panelTitle: String, style: CardStyle) extends BootstrapHtmlComponent {
  override def renderTag(md: ModifierT*): TagT = {
    val titleVar = Var[Frag]("ERROR")
    val successButton = Button(ButtonStyle.success)("Win 10000000$", onclick := Callback.onClick(_ ⇒ TestModal().show()), Tooltip(i("Press me"), TooltipPlacement.left)).render
    val dangerButton = Button(ButtonStyle.danger)("Format C:\\", Popover(span(titleVar), "Popover test", TooltipPlacement.right)).render
    titleVar() = i("Boom")

    val toggleButton = ToggleButton(Bootstrap.button("Toggle me"))
    val disabledButton = DisabledButton(Bootstrap.button("Heavy computation"))

    disabledButton.state.foreach { pressed ⇒
      if (pressed) {
        window.setTimeout(() ⇒ {
          window.alert(s"Answer: ${if (toggleButton.state.now) 321 else 123}")
          disabledButton.state() = false
        }, 1000)
      }
    }

    // Render panel
    val panelId = Bootstrap.newId
    val collapseBtnTitle = Var("ERROR")
    val panel = Card(panelId, style)
      .withHeader("euro".faFwIcon, Card.collapse(panelId, panelTitle, Bootstrap.nbsp, Bootstrap.badge("42")), Card.buttons(
        Card.button("plus".faFwIcon, onclick := Callback.onClick(_ ⇒ window.alert("Panel add"))),
        Card.button("minus".faFwIcon, onclick := Callback.onClick(_ ⇒ window.alert("Panel remove")))
      ))
      .withBody(
        new TestProgressBar(ProgressBarStyle.success, 300 millis),
        Navigation.tabs(
          NavigationTab("Simple buttons", Bootstrap.newId, "remove".faFwIcon, Bootstrap.well(
            GridSystem.mkRow(
              small("Hint: press the green button for reactive forms test", Bootstrap.textStyle.info)
            ),
            GridSystem.mkRow(
              ButtonGroup(ButtonGroupSize.default, successButton, dangerButton)
            ),
            GridSystem.mkRow(Collapse(collapseBtnTitle)(
              GridSystem.row(
                GridSystem.col(6).asDiv(Dropdown("Dropdown", Dropdown.item("Test 1", onclick := Callback.onClick(_ ⇒ window.alert("Test 1"))), Dropdown.item("Test 2"))),
                GridSystem.col(6).asDiv(Dropdown.dropup("Dropup", Dropdown.item("Test 3", onclick := Callback.onClick(_ ⇒ window.alert("Test 3"))), Dropdown.item("Test 4")))
              )
            ))
          )),
          NavigationTab("Reactive buttons", Bootstrap.newId, "play-circle".faFwIcon, Bootstrap.well(
            ButtonGroup(ButtonGroupSize.large, toggleButton, disabledButton)
          ))
        )
      )

    collapseBtnTitle() = "Dropdowns"
    panel
  }
}
