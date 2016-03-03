package com.karasiq.bootstrap.test.frontend

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.grid.GridSystem
import com.karasiq.bootstrap.icons.FontAwesome
import com.karasiq.bootstrap.navbar.{NavigationBar, NavigationTab}
import com.karasiq.bootstrap.panel.PanelStyle
import org.scalajs.dom
import org.scalajs.dom.window
import org.scalajs.jquery._
import rx._

import scala.language.postfixOps
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._

@JSExport
object BootstrapTestApp extends JSApp {
  private implicit val context = implicitly[Ctx.Owner] // Stops working if moved to main(), macro magic

  @JSExport
  override def main(): Unit = {
    jQuery(() ⇒ {
      // Table tab will appear after 3 seconds
      val tableVisible = Var(false)
      window.setTimeout(() ⇒ { tableVisible.update(true) }, 3000)
      val navigationBar = NavigationBar(
        NavigationTab("Table", "table", "table".fontAwesome(FontAwesome.fixedWidth), new TestTable, tableVisible.reactiveShow),
        NavigationTab("Carousel", "carousel", "picture", new TestCarousel("https://upload.wikimedia.org/wikipedia/commons/thumb/5/5e/Big_Wood%2C_N2.JPG/1280px-Big_Wood%2C_N2.JPG")),
        NavigationTab("ToDo list", "todo", "fort-awesome".fontAwesome(FontAwesome.fixedWidth), new TodoList)
      )

      // Render page
      val body = jQuery(dom.document.body)
      body.append(navigationBar.navbar("Scala.js Bootstrap Test").render)
      body.append {
        div(id := "main-container", "container".addClass)(
          GridSystem.mkRow(navigationBar.content)
        ).render
      }

      // Reactive navbar test
      navigationBar.addTabs(NavigationTab("Buttons", "buttons", "log-in", new TestPanel("Serious business panel", PanelStyle.warning)))
      navigationBar.selectTab(1)
    })
  }
}