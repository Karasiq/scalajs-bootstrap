package com.karasiq.bootstrap.context

import scala.language.postfixOps

import rx.{Rx, Var}

import com.karasiq.bootstrap.context.ReactiveBinds._

trait ReactiveImplicits { self: RenderingContext ⇒
  import scalaTags.all._

  def readModifier[P](property: P)(implicit rb: ReactiveRead[Element, P]): Modifier = new Modifier {
    def applyTo(t: Element): Unit = {
      rb.bindRead(t, property)
    }
  }

  def writeModifier[P](property: P)(implicit rb: ReactiveWrite[Element, P]): Modifier = new Modifier {
    def applyTo(t: Element): Unit = {
      rb.bindWrite(t, property)
    }
  }

  implicit class RxVariableOps[T](value: Var[T]) {
    def reactiveRead[EV](event: String, f: (Element, EV) ⇒ T)(implicit
        read: ReactiveRead[Element, EventListener[Element, EV]]
    ): Modifier = {
      readModifier(EventListener[Element, EV](event, (el, ev) ⇒ value() = f(el, ev)))
    }

    def reactiveReadWrite[EV](event: String, read: (Element, EV) ⇒ T, write: (Element, T) ⇒ Unit)(implicit
        rb: ReactiveRead[Element, EventListener[Element, EV]],
        wb: ReactiveWrite[Element, Modify[Element, T]]
    ): Modifier = new Modifier {
      override def applyTo(t: Element): Unit = {
        writeModifier(ReactiveBinds.Modify(value, write)).applyTo(t)
        reactiveRead(event, read).applyTo(t)
      }
    }

    def reactiveInputRead(implicit rb: ReactiveRead[Element, FormValue[T]]): Modifier = {
      readModifier(FormValue(value))
    }

    def reactiveInput(implicit
        rb: ReactiveRead[Element, FormValue[T]],
        wb: ReactiveWrite[Element, FormValue[T]]
    ): Modifier = new Modifier {
      def applyTo(t: Element): Unit = {
        writeModifier(FormValue(value)).applyTo(t)
        readModifier(FormValue(value)).applyTo(t)
      }
    }
  }

  implicit class RxValueOps[T](value: Rx[T]) {
    def reactiveWrite(f: (Element, T) ⇒ Unit)(implicit wb: ReactiveWrite[Element, Modify[Element, T]]): Modifier = {
      writeModifier(ReactiveBinds.Modify(value, f))
    }

    def reactiveInputWrite(implicit wb: ReactiveWrite[Element, FormValue[T]]): Modifier = {
      val rxVar = Var[T](value.now)
      value.foreach(rxVar() = _)
      writeModifier(FormValue(rxVar))
    }
  }

  implicit class RxStateOps(state: Rx[Boolean]) {
    def reactiveShow(implicit wb: ReactiveWrite[Element, Visibility]): Modifier = {
      writeModifier(Visibility(state))(wb)
    }

    def reactiveHide: Modifier = {
      state.map(!_).reactiveShow
    }
  }

  implicit class RxNodeBind[T](value: Rx[T])(implicit wb: ReactiveWrite[Element, BindNode[T]]) extends Modifier {
    override def applyTo(t: Element): Unit = {
      writeModifier(BindNode(value)).applyTo(t)
    }
  }

  implicit class RxModifier(value: Rx[Modifier]) {
    // Explicit
    def auto(implicit wb: ReactiveWrite[Element, Modify[Element, Modifier]]): Modifier = {
      writeModifier(Modify[Element, Modifier](value, (e, v) ⇒ v.applyTo(e)))
    }
  }
}
