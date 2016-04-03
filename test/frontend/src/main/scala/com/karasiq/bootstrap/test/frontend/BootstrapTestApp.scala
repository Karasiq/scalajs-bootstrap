package com.karasiq.bootstrap.test.frontend

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.card.CardStyle
import com.karasiq.bootstrap.grid.GridSystem
import com.karasiq.bootstrap.navbar.Navigation.Tab
import com.karasiq.bootstrap.navbar._
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
      val navigationBar = Navbar()
        .withBrand("Scala.js Bootstrap Test", href := "http://getbootstrap.com/components/#navbar")
        .withTabs(
          Tab("Table", "table", "table", new TestTable, tableVisible.reactiveShow),
          Tab("Carousel", "carousel", "picture-o", new TestCarousel("https://upload.wikimedia.org/wikipedia/commons/thumb/5/5e/Big_Wood%2C_N2.JPG/1280px-Big_Wood%2C_N2.JPG")),
          Tab("ToDo list", "todo", "fort-awesome", new TodoList)
        )
        .withContentContainer(content ⇒ GridSystem.container(id := "main-container", marginTop := 80.px, GridSystem.mkRow(content)))
        .withStyles(NavbarStyle.dark, NavbarStyle.fixedTop, NavbarBackground.primary)
        .build()

      // Render page
      navigationBar.applyTo(dom.document.body)

      // Reactive navbar test
      navigationBar.addTabs(Tab("Buttons", "buttons", "sign-in", new TestCard("Serious business panel", CardStyle.warning)))
      navigationBar.selectTab(1)
    })
  }
}