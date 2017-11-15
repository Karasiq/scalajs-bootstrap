package com.karasiq.bootstrap4.context

import scala.language.{implicitConversions, postfixOps}
import scala.scalajs.js

import org.scalajs.dom

import com.karasiq.bootstrap4.utils.ClassModifiers

trait JSReactiveBinds extends ReactiveBinds { self: JSRenderingContext with ClassModifiers ⇒
  import ReactiveBinds._
  protected type Event = dom.Event

  implicit def rxEventListener[EL <: Element, EV <: Event]: ReactiveRead[EL, EventListener[EL, EV]] = new ReactiveRead[EL, EventListener[EL, EV]] {
    def bindRead(element: EL, property: EventListener[EL, EV]): Unit = {
      val function = js.ThisFunction.fromFunction2 { (el: EL, ev: EV) ⇒
        property.callback(el, ev)
      }
      element.asInstanceOf[js.Dynamic].addEventListener(property.`type`, function)
    }
  }

  implicit def rxModify[E <: Element, T]: ReactiveWrite[E, Modify[E, T]] = new ReactiveWrite[E, Modify[E, T]] {
    def bindWrite(element: E, property: Modify[E, T]): Unit = {
      val elRx = property.value.map(identity)
      elRx.foreach { newValue ⇒
        if (isElementAvailable(element)) {
          property.func(element, newValue)
        } else {
          elRx.kill()
        }
      }
    }
  }

  implicit def rxBindNode[E <: Element, N: Renderable]: ReactiveWrite[E, BindNode[N]] = new ReactiveWrite[E, BindNode[N]] {
    def bindWrite(parent: E, property: BindNode[N]): Unit = {
      val elRx = property.value.map(identity)
      var oldElement = property.value.now.render
      elRx.triggerLater {
        val element = oldElement
        if (isElementAvailable(element) && isElementAvailable(element.parentNode)) {
          val newElement = elRx.now.render
          oldElement = newElement
          element.parentNode.replaceChild(newElement, element)
        } else {
          elRx.kill()
        }
      }
      parent.appendChild(oldElement)
    }
  }

  private[this] final class FormValueRW[E <: Element, T](event: String,
                                                         read: dom.html.Input ⇒ T,
                                                         write: (dom.html.Input, T) ⇒ Unit)
    extends ReactiveRW[E, FormValue[T]] {

    def bindRead(element: E, property: FormValue[T]): Unit = {
      rxEventListener[E, Event].bindRead(element, EventListener(event,
        (e, _) ⇒ property.value() = read(e.asInstanceOf[dom.html.Input])))
    }

    def bindWrite(element: E, property: FormValue[T]): Unit = {
      rxModify[E, T].bindWrite(element, Modify(property.value, { (e, v) ⇒
        val input = e.asInstanceOf[dom.html.Input]
        if (read(input) != v) write(input, v)
      }))
    }
  }

  implicit def rxFormValue[E <: Element]: ReactiveRW[E, FormValue[String]] = {
    new FormValueRW("input", _.value, _.value = _)
  }

  implicit def rxFormValueInt[E <: Element]: ReactiveRW[E, FormValue[Int]] = {
    new FormValueRW("input", _.valueAsNumber, _.valueAsNumber = _)
  }

  implicit def rxFormValueBoolean[E <: Element]: ReactiveRW[E, FormValue[Boolean]] = {
    new FormValueRW("change", _.checked, _.checked = _)
  }

  implicit def rxFormValueStrings[E <: Element]: ReactiveRW[E, FormValue[Seq[String]]] = {
    new FormValueRW("change", { e ⇒
      val select = e.asInstanceOf[dom.html.Select]
      select.options.collect {
        case opt if opt.selected ⇒
          opt.value
      }
    }, (e, v) ⇒ {
      val select = e.asInstanceOf[dom.html.Select]
      select.options.foreach { opt ⇒
        opt.selected = v.contains(opt.value)
      }
    })
  }

  implicit def rxFormValueFiles[E <: Element]: ReactiveRead[E, FormValue[Seq[dom.File]]] = {
    new FormValueRW("change", _.files, (_, _) ⇒ ())
  }

  implicit def rxVisibility[E <: Element]: ReactiveWrite[E, Visibility] = new ReactiveWrite[E, Visibility] {
    def bindWrite(element: E, property: Visibility): Unit = {
      // var oldDisplay = "block"
      rxModify[Element, Boolean].bindWrite(element, Modify(property.visible, { (e, isVisible) ⇒
        /* val htmlElement = e.asInstanceOf[html.Element]
        if (!isVisible) {
          if (htmlElement.style.display != "none") oldDisplay = htmlElement.style.display
          htmlElement.style.display = "none"
        } else if (htmlElement.style.display == "none") {
          htmlElement.style.display = oldDisplay
        } */

        /*
          if (!isVisible) {
            element.classList.add("d-none")
          } else {
            element.classList.remove("d-none")
          }
         */

        if (!isVisible) {
          element.setAttribute("hidden", "")
        } else {
          element.removeAttribute("hidden")
        }
      }))
    }
  }

  @inline
  private[this] def isElementAvailable(e: dom.Node): Boolean = {
    !js.isUndefined(e) && e.ne(null)
  }
}
