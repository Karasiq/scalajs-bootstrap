package com.karasiq.bootstrap.test.frontend

import com.karasiq.bootstrap.Bootstrap.default._
import com.karasiq.bootstrap.test.TestHtmlPage
import org.scalajs.dom
import org.scalajs.dom.window
import org.scalajs.jquery._
import rx._

import scala.language.postfixOps
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
import scalaTags.all._

@JSExport
object BootstrapTestApp extends JSApp {
  @JSExport
  override def main(): Unit = {
    jQuery(() ⇒ {
      // Table tab will appear after 3 seconds
      val tableVisible = Var(false)
      val tabTitle = Var("Wait...")
      window.setTimeout(() ⇒ {
        tableVisible.update(true)
        window.setTimeout(() => { tabTitle() = "Table" }, 1000)
      }, 3000)
      val tabs = Var(Seq[NavigationTab](
        NavigationTab(tabTitle, "table", "table".fontAwesome(FontAwesome.fixedWidth), new TestTable, tableVisible.reactiveShow),
        NavigationTab("Carousel", "carousel", "picture".glyphicon, new TestCarousel("https://upload.wikimedia.org/wikipedia/commons/thumb/5/5e/Big_Wood%2C_N2.JPG/1280px-Big_Wood%2C_N2.JPG")),
        NavigationTab("ToDo list", "todo", "fort-awesome".fontAwesome(FontAwesome.fixedWidth), new TodoList),
        NavigationTab("Text rendering", "text", "envelope".glyphicon, Bootstrap.jumbotron(
          FormInput.textArea(a("Text rendering", href := "serverside.html"), rows := 10, readonly, TestHtmlPage())
        ))
      ))
      val navigationBar = NavigationBar()
        .withBrand("Scala.js Bootstrap Test", href := "http://getbootstrap.com/components/#navbar")
        .withTabs(tabs)
        .withContentContainer(content ⇒ GridSystem.container(id := "main-container", GridSystem.mkRow(content)))
        .withStyles(NavigationBarStyle.inverse, NavigationBarStyle.fixedTop)
        .build()

      // Render page
      navigationBar.applyTo(dom.document.body)

      // Reactive navbar test
      tabs() = tabs.now :+ NavigationTab("Buttons", "buttons", "log-in".glyphicon, new TestPanel("Serious business panel", PanelStyle.warning))
      navigationBar.selectTab(2)
    })
  }
}