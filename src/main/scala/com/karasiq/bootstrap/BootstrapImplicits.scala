package com.karasiq.bootstrap

import com.karasiq.bootstrap.buttons._
import org.scalajs.dom
import org.scalajs.dom.Element
import org.scalajs.jquery.JQuery
import rx.{Obs, Rx, Var}

import scala.language.implicitConversions
import scala.scalajs.js
import scalatags.JsDom.all._

@js.native
trait BootstrapJQuery extends js.Object {
  def tab(arg: String): Unit = js.native
  def carousel(options: js.Any = ???): Unit = js.native
  def modal(options: js.Any = ???): Unit = js.native
}

object BootstrapImplicits {
  private type HtmlButton = ConcreteHtmlTag[dom.html.Button]

  implicit def bootstrapJquery(jq: JQuery): BootstrapJQuery = {
    jq.asInstanceOf[BootstrapJQuery]
  }

  implicit class ButtonOps(val button: HtmlButton) extends AnyVal {
    def toggleButton: ToggleButton = new ToggleButton(button)
    def disabledButton: DisabledButton = new DisabledButton(button)
  }

  implicit def bootstrapHtmlComponentToTag[T <: dom.Element](bc: BootstrapHtmlComponent[T]): ConcreteHtmlTag[T] = {
    bc.renderTag()
  }

  implicit def renderBootstrapComponent(bc: BootstrapComponent): Modifier = {
    bc.render
  }

  implicit class RxValueOps[T](state: Rx[T]) {
    def rxModifier(f: (dom.Element, T) ⇒ Unit): Modifier = new Modifier {
      override def applyTo(t: Element): Unit = {
        Obs(state) {
          f(t, state.now)
        }
      }
    }
  }

  implicit class RxStateOps(val state: Rx[Boolean]) extends AnyVal {
    def rxShow: Modifier = {
      val oldDisplay = Var("block")
      state.rxModifier { (e, state) ⇒
        if (!state) {
          oldDisplay.updateSilent(e.asInstanceOf[dom.html.Element].style.display)
          e.asInstanceOf[dom.html.Element].style.display = "none"
        } else if (e.asInstanceOf[dom.html.Element].style.display == "none") {
          e.asInstanceOf[dom.html.Element].style.display = oldDisplay.now
        }
      }
    }

    def rxHide: Modifier = {
      Rx(!state()).rxShow
    }
  }

  implicit def bindRxAttr[T](implicit ev: AttrValue[T]): AttrValue[Rx[T]] = new AttrValue[Rx[T]] {
    override def apply(t: Element, a: Attr, v: Rx[T]): Unit = {
      Obs(v, "rx-attr-updater") {
        ev.apply(t, a, v.now)
      }
    }
  }

  implicit class RxNode(rx: Rx[dom.Node]) extends Modifier {
    override def applyTo(t: Element): Unit = {
      val container = Var(rx.now)
      Obs(rx, "rx-dom-updater", skipInitial = true) {
        val element = container.now
        val newElement = rx.now
        container.updateSilent(newElement)
        element.parentNode.replaceChild(newElement, element)
      }
      container.now.applyTo(t)
    }
  }

  implicit class RxFragNode[T](value: Rx[T])(implicit ev: T ⇒ Frag) extends Modifier {
    override def applyTo(t: Element): Unit = {
      new RxNode(Rx(value().render)).applyTo(t)
    }
  }

  implicit class HtmlClassOptOps(val className: Option[String]) extends AnyVal {
    def classOpt: Modifier = new Modifier {
      override def applyTo(t: Element): Unit = {
        className.foreach(t.classList.add)
      }
    }
  }

  //noinspection MutatorLikeMethodIsParameterless
  implicit class HtmlClassOps(val className: String) extends AnyVal {
    def addClass: Modifier = new Modifier {
      override def applyTo(t: Element): Unit = {
        t.classList.add(className)
      }
    }

    def removeClass: Modifier = new Modifier {
      override def applyTo(t: Element): Unit = {
        t.classList.remove(className)
      }
    }

    def classIf(state: Boolean): Modifier = new Modifier {
      override def applyTo(t: Element): Unit = {
        if (state) {
          t.classList.add(className)
        } else {
          t.classList.remove(className)
        }
      }
    }

    def classIf(state: Rx[Boolean]): Modifier = state.rxModifier { (e, state) ⇒
      classIf(state).applyTo(e)
    }
  }
}
