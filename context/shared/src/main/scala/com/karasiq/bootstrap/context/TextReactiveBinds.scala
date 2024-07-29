package com.karasiq.bootstrap.context

import scala.language.postfixOps
import scalatags.text.Builder
import scalatags.text.Builder.GenericAttrValueSource

import com.karasiq.bootstrap.context.ReactiveBinds.FormValue

//noinspection ConvertExpressionToSAM
// Reactive emulation
trait TextReactiveBinds extends ReactiveBinds { self: TextRenderingContext ⇒
  override protected type Event = Any

  private[this] val NO_OP_BIND: ReactiveRW[Element, Nothing] = new ReactiveRW[Element, Nothing] {
    def bindWrite(element: Builder, property: Nothing): Unit = ()
    def bindRead(element: Builder, property: Nothing): Unit  = ()
  }

  implicit def rxEventListener[EL <: Element, EV <: Event]: ReactiveRead[EL, ReactiveBinds.EventListener[EL, EV]] = {
    NO_OP_BIND.asInstanceOf[ReactiveRead[EL, ReactiveBinds.EventListener[EL, EV]]]
  }

  implicit def rxModify[E <: Element, T]: ReactiveWrite[E, ReactiveBinds.Modify[E, T]] = {
    new ReactiveWrite[E, ReactiveBinds.Modify[E, T]] {
      def bindWrite(element: E, property: ReactiveBinds.Modify[E, T]): Unit = {
        property.func(element, property.value.now)
      }
    }
  }

  implicit def rxBindNode[E <: Element, N: Renderable]: ReactiveWrite[E, ReactiveBinds.BindNode[N]] = {
    new ReactiveWrite[E, ReactiveBinds.BindNode[N]] {
      def bindWrite(element: E, property: ReactiveBinds.BindNode[N]): Unit = {
        property.value.now.applyTo(element)
      }
    }
  }

  implicit def rxFormValue[E <: Element]: ReactiveRW[E, ReactiveBinds.FormValue[String]] = {
    new ReactiveRW[E, FormValue[String]] {
      def bindWrite(element: E, property: FormValue[String]): Unit = {
        element.setAttr("value", GenericAttrValueSource(property.value.now))
      }

      def bindRead(element: E, property: FormValue[String]): Unit = ()
    }
  }

  implicit def rxFormValueInt[E <: Element]: ReactiveRW[E, ReactiveBinds.FormValue[Int]] = {
    new ReactiveRW[E, FormValue[Int]] {
      def bindWrite(element: E, property: FormValue[Int]): Unit = {
        element.setAttr("value", GenericAttrValueSource(property.value.now.toString))
      }

      def bindRead(element: E, property: FormValue[Int]): Unit = ()
    }
  }

  implicit def rxFormValueDouble[E <: Element]: ReactiveRW[E, ReactiveBinds.FormValue[Double]] = {
    new ReactiveRW[E, FormValue[Double]] {
      def bindWrite(element: E, property: FormValue[Double]): Unit = {
        element.setAttr("value", GenericAttrValueSource(property.value.now.toString))
      }

      def bindRead(element: E, property: FormValue[Double]): Unit = ()
    }
  }

  implicit def rxFormValueBoolean[E <: Element]: ReactiveRW[E, ReactiveBinds.FormValue[Boolean]] = {
    new ReactiveRW[E, FormValue[Boolean]] {
      def bindWrite(element: E, property: FormValue[Boolean]): Unit = {
        if (property.value.now) {
          element.setAttr("checked", GenericAttrValueSource(""))
        }
      }

      def bindRead(element: E, property: FormValue[Boolean]): Unit = ()
    }
  }

  implicit def rxFormValueStrings[E <: Element]: ReactiveRW[E, ReactiveBinds.FormValue[Seq[String]]] = {
    NO_OP_BIND.asInstanceOf[ReactiveRW[E, FormValue[Seq[String]]]] // Not implemented
  }

  implicit def rxVisibility[E <: Element]: ReactiveWrite[E, ReactiveBinds.Visibility] = {
    new ReactiveWrite[E, ReactiveBinds.Visibility] {
      def bindWrite(element: E, property: ReactiveBinds.Visibility): Unit = {
        if (!property.visible.now) {
          addClass(element, "hide")
        } else {
          removeClass(element, "hide")
        }
      }
    }
  }
}
