package com.karasiq.bootstrap.context

import scala.language.postfixOps

import rx._

import com.karasiq.bootstrap.context.ReactiveBinds._

trait ReactiveRead[Element, Property] {
  def bindRead(element: Element, property: Property): Unit
}

trait ReactiveWrite[Element, Property] {
  def bindWrite(element: Element, property: Property): Unit
}

trait ReactiveRW[Element, Property] extends ReactiveRead[Element, Property] with ReactiveWrite[Element, Property]

object ReactiveBinds {
  case class EventListener[EL, EV](`type`: String, callback: (EL, EV) ⇒ Unit)
  case class Modify[E, V](value: Rx[V], func: (E, V) ⇒ Unit)
  case class BindNode[N](value: Rx[N])
  case class FormValue[T](value: Var[T])
  case class Visibility(visible: Rx[Boolean])
}

/** Predefined binds
  */
trait ReactiveBinds { self: RenderingContext ⇒
  protected type Event
  protected type Renderable[T] = T ⇒ scalaTags.Frag

  implicit val scalaRxContext: Ctx.Owner
  implicit def rxEventListener[EL <: Element, EV <: Event]: ReactiveRead[EL, EventListener[EL, EV]]
  implicit def rxModify[E <: Element, T]: ReactiveWrite[E, Modify[E, T]]
  implicit def rxBindNode[E <: Element, N: Renderable]: ReactiveWrite[E, BindNode[N]]
  implicit def rxFormValue[E <: Element]: ReactiveRW[E, FormValue[String]]
  implicit def rxFormValueInt[E <: Element]: ReactiveRW[E, FormValue[Int]]
  implicit def rxFormValueDouble[E <: Element]: ReactiveRW[E, FormValue[Double]]
  implicit def rxFormValueBoolean[E <: Element]: ReactiveRW[E, FormValue[Boolean]]
  implicit def rxFormValueStrings[E <: Element]: ReactiveRW[E, FormValue[Seq[String]]]
  implicit def rxVisibility[E <: Element]: ReactiveWrite[E, Visibility]
}
