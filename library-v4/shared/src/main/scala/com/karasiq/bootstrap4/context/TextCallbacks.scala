package com.karasiq.bootstrap4.context

import scala.language.postfixOps
import scalatags.generic.{Attr, AttrValue}

import com.karasiq.bootstrap4.utils.Callbacks

trait TextCallbacks extends Callbacks { self: RenderingContext ⇒
  type Callback = Unit

  protected type ClickElement = Any
  protected type InputElement = Any
  protected type FormElement = Any

  object Callback extends CallbackFactory {
    def onClick(f: ClickElement => Unit): Callback = {
      ()
    }

    def onInput(f: (InputElement) => Unit): Callback = {
      ()
    }

    def onSubmit(f: (FormElement) => Unit): Callback = {
      ()
    }
  }

  implicit val callbackValue: AttrValue[Element, Callback] = new AttrValue[Element, Callback] {
    def apply(t: Element, a: Attr, v: Callback): Unit = ()
  }
}
