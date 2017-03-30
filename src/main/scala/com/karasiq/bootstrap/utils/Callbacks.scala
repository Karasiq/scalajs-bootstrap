package com.karasiq.bootstrap.utils

import scala.language.postfixOps
import scalatags.generic.AttrValue

trait Callbacks {
  type Callback
  type Element
  type InputElement <: Element
  type FormElement <: Element

  trait CallbackFactory {
    def onClick(f: Element ⇒ Unit): Callback
    def onInput(f: InputElement ⇒ Unit): Callback
    def onSubmit(f: FormElement ⇒ Unit): Callback
  }

  val Callback: CallbackFactory
  implicit def callbackValue: AttrValue[Element, Callback]
}
