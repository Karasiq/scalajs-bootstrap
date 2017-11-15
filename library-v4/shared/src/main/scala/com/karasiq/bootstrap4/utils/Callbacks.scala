package com.karasiq.bootstrap4.utils

import scala.language.postfixOps
import scalatags.generic.AttrValue

import com.karasiq.bootstrap4.context.RenderingContext

trait Callbacks { self: RenderingContext ⇒
  type Callback
  protected type ClickElement
  protected type InputElement
  protected type FormElement

  trait CallbackFactory {
    def onClick(f: ClickElement ⇒ Unit): Callback
    def onInput(f: InputElement ⇒ Unit): Callback
    def onSubmit(f: FormElement ⇒ Unit): Callback
  }

  val Callback: CallbackFactory
  implicit def callbackValue: AttrValue[Element, Callback]
}
