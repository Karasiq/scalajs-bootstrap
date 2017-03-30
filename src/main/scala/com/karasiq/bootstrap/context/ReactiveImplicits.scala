package com.karasiq.bootstrap.context

import com.karasiq.bootstrap.context.ReactiveBinds._
import rx.{Rx, Var}

import scala.language.postfixOps

trait ReactiveImplicits { self: RenderingContext ⇒
  import scalaTags._

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
    def reactiveRead(event: String, f: Element ⇒ T)(implicit read: ReactiveRead[Element, EventListener[Element, T]]): Modifier = {
      readModifier(EventListener[Element, T](event, (e, _) ⇒ value() = f(e)))
    }

    def reactiveReadWrite(event: String, read: Element ⇒ T, write: (Element, T) ⇒ Unit)
                         (implicit rb: ReactiveRead[Element, EventListener[Element, T]],
                          wb: ReactiveWrite[Element, Modify[Element, T]]): Modifier = new Modifier {
      override def applyTo(t: Element): Unit = {
        writeModifier(ReactiveBinds.Modify(value, write)).applyTo(t)
        reactiveRead(event, read)
      }
    }

    def reactiveInputRead(implicit rb: ReactiveRead[Element, FormValue[T]]): Modifier = {
      readModifier(FormValue(value))
    }

    def reactiveInput(implicit rb: ReactiveRead[Element, FormValue[T]],
                      wb: ReactiveWrite[Element, FormValue[T]]): Modifier = new Modifier {
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

  implicit class RxNode(rx: Rx[FragT])(implicit wb: ReactiveWrite[Element, BindNode[FragT]]) extends Modifier {
    override def applyTo(t: Element): Unit = {
      writeModifier(BindNode(rx)).applyTo(t)
    }
  }

  implicit class RxFragNode[T](value: Rx[T])(implicit ev: T ⇒ Frag, wb: ReactiveWrite[Element, BindNode[FragT]]) extends Modifier {
    override def applyTo(t: Element): Unit = {
      new RxNode(value.map(_.render)).applyTo(t)
    }
  }

  implicit class RxModifier(value: Rx[Modifier]) {
    // Explicit
    def auto(implicit wb: ReactiveWrite[Element, Modify[Element, Modifier]]): Modifier = {
      writeModifier(Modify[Element, Modifier](value, (e, v) ⇒ v.applyTo(e)))
    }
  }
}
