package com.karasiq.bootstrap

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.buttons.Button
import com.karasiq.bootstrap.icons.{FontAwesome, IconModifier}
import org.scalajs.dom
import org.scalajs.dom.raw.{Event, MouseEvent}

import scala.scalajs.js
import scalatags.JsDom.all._

object Bootstrap {
  /**
    * Jumbotron
    * @see [[https://getbootstrap.com/components/#jumbotron]]
    */
  def jumbotron: ConcreteHtmlTag[dom.html.Div] = div("jumbotron".addClass)

  /**
    * Default button
    */
  def button: ConcreteHtmlTag[dom.html.Button] = Button()

  /**
    * Blockquote
    */
  def blockquote(inverse: Boolean = false): Tag = {
    val tag = "blockquote".tag
    tag(if (inverse) "blockquote-inverse".addClass else "blockquote".addClass)
  }

  /**
    * Font awesome icon with fixed width
    * @param name Icon name
    */
  def icon(name: String): IconModifier = {
    name.fontAwesome(FontAwesome.fixedWidth)
  }

  private var idCounter: Int = 0

  /**
    * Generates unique element ID
    */
  def newId: String = {
    idCounter = idCounter + 1
    s"bs-auto-$idCounter"
  }

  def jsClick(f: dom.Element ⇒ Unit): js.Function = {
    js.ThisFunction.fromFunction2 { (element: dom.Element, ev: MouseEvent) ⇒
      if (ev.button == 0 && !(ev.shiftKey || ev.altKey || ev.metaKey || ev.ctrlKey)) {
        ev.preventDefault()
        f(element)
      }
    }
  }

  def jsInput(f: dom.html.Input ⇒ Unit): js.Function = {
    js.ThisFunction.fromFunction2 { (element: dom.html.Input, ev: Event) ⇒
      // ev.preventDefault()
      f(element)
    }
  }

  def jsSubmit(f: dom.html.Form ⇒ Unit): js.Function = {
    js.ThisFunction.fromFunction2 { (element: dom.html.Form, ev: Event) ⇒
      ev.preventDefault()
      f(element)
    }
  }
}