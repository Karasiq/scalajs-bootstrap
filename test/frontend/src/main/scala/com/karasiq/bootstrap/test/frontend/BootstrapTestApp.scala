package com.karasiq.bootstrap.test.frontend


import com.karasiq.bootstrap.Bootstrap
import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.buttons._
import com.karasiq.bootstrap.carousel.Carousel
import com.karasiq.bootstrap.collapse.Collapse
import com.karasiq.bootstrap.dropdown.Dropdown
import com.karasiq.bootstrap.grid.GridSystem.{col, row}
import com.karasiq.bootstrap.modal.Modal
import com.karasiq.bootstrap.navbar.{NavigationBar, NavigationTab}
import com.karasiq.bootstrap.panel.{Panel, PanelBuilder, PanelStyle}
import com.karasiq.bootstrap.table.{PagedTable, TableRow, TableStyles}
import org.scalajs.dom
import org.scalajs.jquery._
import rx._

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._

@JSExport
object BootstrapTestApp extends JSApp {
  private def testButtons: Modifier = {
    val successButton = ButtonBuilder(ButtonStyle.success)("Win 10000000$", onclick := Bootstrap.jsClick { _ ⇒
      Modal("Lottery", "You won 10000000$")
        .withButtons(Modal.closeButton(), Modal.button("Take", Modal.dismiss))
        .show()
    }).render
    val dangerButton = ButtonBuilder(ButtonStyle.danger)("Format C:\\", onclick := Bootstrap.jsClick(_ ⇒ dom.alert("Boom"))).render

    val toggleButton = Bootstrap.button("Toggle me").toggleButton
    val disabledButton = Bootstrap.button("Heavy computation").disabledButton

    Obs(disabledButton.state, "disabled-button-click-handler") {
      if (disabledButton.state.now) {
        dom.setTimeout(() ⇒ {
          dom.alert("Answer: 123")
          disabledButton.state.update(false)
        }, 1000)
      }
    }

    // Render panel
    import Panel._
    val panelId = Bootstrap.newId
    PanelBuilder(panelId, PanelStyle.warning)
      .withHeader(title("euro", collapse(panelId, "Serious business panel"), buttons(
        button("plus", onclick := Bootstrap.jsClick(_ ⇒ dom.alert("Panel add"))),
        button("minus", onclick := Bootstrap.jsClick(_ ⇒ dom.alert("Panel remove")))
      )))
      .build(
        Collapse("Dropdowns")(
          Dropdown("Dropdown", Dropdown.item("Test 1", onclick := Bootstrap.jsClick(_ ⇒ dom.alert("Test 1"))), Dropdown.item("Test 2")),
          Dropdown.dropup("Dropup", Dropdown.item("Test 3", onclick := Bootstrap.jsClick(_ ⇒ dom.alert("Test 3"))), Dropdown.item("Test 4"))
        ),
        ButtonToolbar(ButtonGroup(ButtonGroupSize.default, successButton, dangerButton), ButtonGroup(ButtonGroupSize.large, toggleButton, disabledButton))
      )
  }

  private def testPagedTable: Modifier = {
    val container = div(`class` := "table-responsive").render

    // Table content
    val reactiveColumn = Var(2)
    val heading = Var(Seq[Modifier]("First", "Second", "Third"))
    val content = Var(for (i <- 1 to 45) yield TableRow(Seq(i, i + 1, Rx(i + reactiveColumn())), onclick := Bootstrap.jsClick { row ⇒
      reactiveColumn.update(reactiveColumn() + 1)
      row.classList.add("success")
    }))

    // Render table
    val pagedTable = PagedTable(heading, content, 10)
    container.appendChild(pagedTable.renderTag(TableStyles.bordered, TableStyles.hover, TableStyles.striped).render)

    // Test reactive components
    pagedTable.currentPage.update(2)
    content.update(content().reverse)
    heading.update(Seq("Eins", "Zwei", Rx("Drei " + reactiveColumn())))
    container
  }

  private def testCarousel: Modifier = {
    val imgSrc: String = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5e/Big_Wood%2C_N2.JPG/1280px-Big_Wood%2C_N2.JPG"
    Carousel(
      Carousel.slide(
        imgSrc,
        h3("First slide label"),
        p("Nulla vitae elit libero, a pharetra augue mollis interdum.")
      ),
      Carousel.slide(
        imgSrc,
        h3("Second slide label"),
        p("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
      )
    )
  }

  @JSExport
  override def main(): Unit = {
    jQuery(() ⇒ {
      // Create navigation elements
      val navigationBar = new NavigationBar("bstest")
      navigationBar.setTabs(
        NavigationTab("Table", "table", "briefcase", this.testPagedTable),
        NavigationTab("Carousel", "carousel", "picture", this.testCarousel)
      )

      // Bootstrap container
      val container = div(id := "main-container", `class` := "container")(
        row(col.md(12)(
          navigationBar.content
        ))
      ).render

      // Render page
      val body = jQuery(dom.document.body)
      body.append(navigationBar.navbar("Scala.js Bootstrap Test").render)
      body.append(container)

      // Reactive navbar test
      navigationBar.addTabs(NavigationTab("Buttons", "buttons", "log-in", this.testButtons))
      navigationBar.selectTab(1)
    })
  }
}