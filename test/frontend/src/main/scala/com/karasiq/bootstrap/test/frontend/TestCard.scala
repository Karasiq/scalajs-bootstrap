package com.karasiq.bootstrap.test.frontend

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.buttons._
import com.karasiq.bootstrap.card.{Card, CardStyle}
import com.karasiq.bootstrap.collapse.Collapse
import com.karasiq.bootstrap.dropdown.Dropdown
import com.karasiq.bootstrap.grid.GridSystem
import com.karasiq.bootstrap.navbar.Navigation
import com.karasiq.bootstrap.navbar.Navigation.Tab
import com.karasiq.bootstrap.popover.Popover
import com.karasiq.bootstrap.progressbar.ProgressBarStyle
import com.karasiq.bootstrap.tooltip.{Tooltip, TooltipPlacement}
import com.karasiq.bootstrap.{Bootstrap, BootstrapHtmlComponent}
import org.scalajs.dom
import org.scalajs.dom.window
import rx._

import scala.concurrent.duration._
import scala.language.postfixOps
import scalatags.JsDom.all._

final class TestCard(cardTitle: String, style: CardStyle)(implicit ctx: Ctx.Owner) extends BootstrapHtmlComponent[dom.html.Div] {
  override def renderTag(md: Modifier*): RenderedTag = {
    val successButton = Button(ButtonStyle.outline(ButtonStyle.success))("Win 10000000$", onclick := Bootstrap.jsClick(_ ⇒ TestModal().show()), Tooltip("Press me", TooltipPlacement.left)).render
    val dangerButton = Button(ButtonStyle.danger)("Format C:\\", Popover("Boom", "Popover test", TooltipPlacement.top)).render

    val toggleButton = Bootstrap.button("Toggle me").toggleButton
    val disabledButton = Bootstrap.button("Heavy computation").disabledButton

    disabledButton.state.foreach { pressed ⇒
      if (pressed) {
        window.setTimeout(() ⇒ {
          window.alert(s"Answer: ${if (toggleButton.state.now) 321 else 123}")
          disabledButton.state.update(false)
        }, 1000)
      }
    }

    // Render panel
    import Card._
    val cardId = Bootstrap.newId
    Card(cardId)
      .withHeader(Bootstrap.icon("euro"), collapse(cardId, cardTitle), buttons(
        button("plus", onclick := Bootstrap.jsClick(_ ⇒ window.alert("Panel add"))),
        button("minus", onclick := Bootstrap.jsClick(_ ⇒ window.alert("Panel remove")))
      ))
      .renderTag(
        new TestProgressBar(ProgressBarStyle.success, 300 millis),
        Navigation.tabs(
          Tab("Simple buttons", Bootstrap.newId, "remove", div(
            GridSystem.mkRow(
              small("Hint: press the green button for reactive forms test")
            ),
            GridSystem.mkRow(
              ButtonGroup(ButtonGroupSize.default, successButton, dangerButton)
            ),
            GridSystem.mkRow(Collapse("Dropdowns")(
              GridSystem.row(
                GridSystem.col(6).asDiv(Dropdown("Dropdown", Dropdown.item("Test 1", onclick := Bootstrap.jsClick(_ ⇒ window.alert("Test 1"))), Dropdown.item("Test 2"))),
                GridSystem.col(6).asDiv(Dropdown.dropup("Dropup", Dropdown.item("Test 3", onclick := Bootstrap.jsClick(_ ⇒ window.alert("Test 3"))), Dropdown.item("Test 4")))
              )
            ))
          )),
          Tab("Reactive buttons", Bootstrap.newId, "play-circle", div(
            ButtonGroup(ButtonGroupSize.large, toggleButton, disabledButton)
          ))
        )
      )
  }
}
