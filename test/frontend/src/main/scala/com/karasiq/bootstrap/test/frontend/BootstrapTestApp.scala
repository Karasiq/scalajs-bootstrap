package com.karasiq.bootstrap.test.frontend

import scala.language.postfixOps

import org.scalajs.dom
import org.scalajs.dom.window
import rx._

import com.karasiq.bootstrap.Bootstrap.default._
import scalaTags.all._

import com.karasiq.bootstrap.jquery.BootstrapJQueryContext

object BootstrapTestApp {
  def main(args: Array[String]): Unit = {
    BootstrapJQueryContext.useNpmImports()
    jQuery(() ⇒ {
      // Table tab will appear after 3 seconds
      val tableVisible = Var(false)
      val tabTitle = Var("Wait...")

      // Show table tab in 3 seconds
      window.setTimeout(() ⇒ {
        tableVisible.update(true)
        window.setTimeout(() ⇒ { tabTitle() = "Table" }, 1000)
      }, 3000)
      
      val tabs = Var(Seq[NavigationTab](
        NavigationTab(tabTitle, "table", "table".faFwIcon, TestTable(), tableVisible.reactiveShow),
        NavigationTab("Carousel", "carousel", "picture".glyphicon, TestCarousel("https://upload.wikimedia.org/wikipedia/commons/thumb/5/5e/Big_Wood%2C_N2.JPG/1280px-Big_Wood%2C_N2.JPG")),
        NavigationTab("ToDo list", "todo", "fort-awesome".faFwIcon, TodoList()),
        NavigationTab("Text rendering", "text", "envelope".glyphicon, Bootstrap.jumbotron(
          FormInput.textArea(a("Text rendering", href := "./serverside.html"), rows := 30, readonly, TestHtmlPage())
        ))
      ))

      val navigationBar = NavigationBar()
        .withBrand("Scala.js Bootstrap Test", href := "https://github.com/Karasiq/scalajs-bootstrap")
        .withTabs(tabs)
        .withContentContainer(content ⇒ GridSystem.container(id := "main-container", GridSystem.mkRow(content)))
        .withStyles(NavigationBarStyle.inverse, NavigationBarStyle.fixedTop)
        .build()

      // Render page
      navigationBar.applyTo(dom.document.body)

      // Reactive navbar test
      tabs() = tabs.now :+ NavigationTab("Buttons", "buttons", "log-in".glyphicon, TestPanel("Serious business panel", PanelStyle.warning))
      navigationBar.selectTab(2)
    })
  }
}