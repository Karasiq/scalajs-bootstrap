package com.karasiq.bootstrap.context

import scala.language.{implicitConversions, postfixOps}
import scala.scalajs.js
import scala.util.{Success, Try}

import org.scalajs.dom
import org.scalajs.dom.html.Input

//noinspection ConvertExpressionToSAM
trait JSReactiveBinds extends ReactiveBinds {
  self: JSRenderingContext with ClassModifiers ⇒

  import ReactiveBinds._

  protected type Event = dom.Event

  implicit def rxEventListener[EL <: Element, EV <: Event]: ReactiveRead[EL, EventListener[EL, EV]] =
    new ReactiveRead[EL, EventListener[EL, EV]] {
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

  implicit def rxBindNode[E <: Element, N: Renderable]: ReactiveWrite[E, BindNode[N]] =
    new ReactiveWrite[E, BindNode[N]] {
      def bindWrite(parent: E, property: BindNode[N]): Unit = {
        val elRx       = property.value.map(identity)
        var oldElement = property.value.now.render
        elRx.triggerLater({
          val element = oldElement
          if (isElementAvailable(element) && isElementAvailable(element.parentNode)) {
            val newElement = elRx.now.render
            oldElement = newElement
            element.parentNode.replaceChild(newElement, element)
          } else {
            elRx.kill()
          }
        }: Unit)
        parent.appendChild(oldElement)
      }
    }

  private[this] final case class FormValueRW[E <: Element, T](
      event: String,
      read: dom.html.Input ⇒ T,
      write: (dom.html.Input, T) ⇒ Unit
  ) extends ReactiveRW[E, FormValue[T]] {

    private[this] def safeRead(e: Element): Try[T] =
      Try(read(e.asInstanceOf[Input])).filter(v ⇒ v != null && !js.isUndefined(v))

    def bindRead(element: E, property: FormValue[T]): Unit = {
      rxEventListener[E, Event].bindRead(
        element,
        EventListener(event, (e, _) ⇒ safeRead(e).foreach(property.value() = _))
      )
    }

    def bindWrite(element: E, property: FormValue[T]): Unit = {
      rxModify[E, T].bindWrite(
        element,
        Modify(
          property.value,
          { (e, v) ⇒
            val input = e.asInstanceOf[dom.html.Input]
            if (safeRead(input) != Success(v)) write(input, v)
          }
        )
      )
    }
  }

  implicit def rxFormValue[E <: Element]: ReactiveRW[E, FormValue[String]] = {
    FormValueRW(
      "input",
      _.value match {
        case s: String if !js.isUndefined(s) ⇒ s
        case _                               ⇒ ""
      },
      _.value = _
    )
  }

  implicit def rxFormValueInt[E <: Element]: ReactiveRW[E, FormValue[Int]] = {
    FormValueRW(
      "input",
      e ⇒ Try(e.value.toInt).getOrElse(0),
      _.valueAsNumber = _
    )
  }

  implicit def rxFormValueDouble[E <: Element]: ReactiveRW[E, FormValue[Double]] = {
    FormValueRW(
      "input",
      e ⇒ Try(e.value.toDouble).getOrElse(Double.NaN),
      _.valueAsNumber = _
    )
  }

  implicit def rxFormValueBoolean[E <: Element]: ReactiveRW[E, FormValue[Boolean]] = {
    FormValueRW("change", _.checked, _.checked = _)
  }

  implicit def rxFormValueStrings[E <: Element]: ReactiveRW[E, FormValue[Seq[String]]] = {
    FormValueRW(
      "change",
      { e ⇒
        val select = e.asInstanceOf[dom.html.Select]
        select.options.collect {
          case opt if opt.selected ⇒
            opt.value
        }.toVector
      },
      (e, v) ⇒ {
        val select = e.asInstanceOf[dom.html.Select]
        select.options.foreach { opt ⇒
          opt.selected = v.contains(opt.value)
        }
      }
    )
  }

  implicit def rxFormValueFiles[E <: Element]: ReactiveRead[E, FormValue[Seq[dom.File]]] = {
    FormValueRW("change", _.files, (_, _) ⇒ ())
  }

  implicit def rxVisibility[E <: Element]: ReactiveWrite[E, Visibility] = new ReactiveWrite[E, Visibility] {
    def bindWrite(element: E, property: Visibility): Unit = {
      // var oldDisplay = "block"
      rxModify[Element, Boolean].bindWrite(
        element,
        Modify(
          property.visible,
          { (e, isVisible) ⇒
            /* val htmlElement = e.asInstanceOf[html.Element]
        if (!isVisible) {
          if (htmlElement.style.display != "none") oldDisplay = htmlElement.style.display
          htmlElement.style.display = "none"
        } else if (htmlElement.style.display == "none") {
          htmlElement.style.display = oldDisplay
        } */
            if (!isVisible) {
              addClass(element, "hide")
            } else {
              removeClass(element, "hide")
            }
          }
        )
      )
    }
  }

  @inline
  private[this] def isElementAvailable(e: dom.Node): Boolean = {
    !js.isUndefined(e) && e.ne(null)
  }
}
