package com.karasiq.bootstrap.context

import scala.language.postfixOps
import scala.scalajs.js
import scalatags.generic.{Attr, AttrValue}

import org.scalajs.dom
import org.scalajs.dom.raw.MouseEvent

trait JSCallbacks extends Callbacks { self: JSRenderingContext ⇒
  type Callback     = js.Function
  type ClickElement = dom.html.Element
  type InputElement = dom.html.Input
  type FormElement  = dom.html.Form

  object Callback extends CallbackFactory {
    def onClick(f: ClickElement ⇒ Unit): js.Function = {
      js.ThisFunction.fromFunction2 { (element: dom.html.Element, ev: MouseEvent) ⇒
        if (ev.button == 0 && !(ev.shiftKey || ev.altKey || ev.metaKey || ev.ctrlKey)) {
          ev.preventDefault()
          f(element)
        }
      }
    }

    def onInput(f: InputElement ⇒ Unit): js.Function = {
      js.ThisFunction.fromFunction2 { (element: dom.html.Input, ev: Event) ⇒
        // ev.preventDefault()
        f(element)
      }
    }

    def onSubmit(f: FormElement ⇒ Unit): js.Function = {
      js.ThisFunction.fromFunction2 { (element: dom.html.Form, ev: Event) ⇒
        ev.preventDefault()
        f(element)
      }
    }
  }

  implicit def callbackValue: AttrValue[Element, js.Function] = new AttrValue[Element, js.Function] {
    def apply(t: Element, a: Attr, v: js.Function): Unit = {
      scalatags.JsDom.all.bindJsAny.apply(t, a, v)
    }
  }
}
