package com.karasiq.bootstrap

import com.karasiq.bootstrap.BootstrapImplicits.RxNode
import com.karasiq.bootstrap.buttons._
import com.karasiq.bootstrap.icons._
import org.scalajs.dom
import org.scalajs.dom.{DOMList, Element, html}
import org.scalajs.jquery.JQuery
import rx._

import scala.language.implicitConversions
import scala.scalajs.js
import scalatags.JsDom.all._

@js.native
trait BootstrapJQuery extends js.Object {
  def tab(options: js.Any): JQuery = js.native
  def carousel(options: js.Any = ???): JQuery = js.native
  def modal(options: js.Any = ???): JQuery = js.native
  def tooltip(options: js.Any = ???): JQuery = js.native
  def popover(options: js.Any = ???): JQuery = js.native
}

object BootstrapImplicits {
  private type HtmlButton = ConcreteHtmlTag[dom.html.Button]

  implicit def bootstrapJquery(jq: JQuery): BootstrapJQuery = {
    jq.asInstanceOf[BootstrapJQuery]
  }

  implicit class ButtonOps(private val button: HtmlButton) extends AnyVal {
    def toggleButton(implicit ctx: Ctx.Owner): ToggleButton = new ToggleButton(button)
    def disabledButton(implicit ctx: Ctx.Owner): DisabledButton = new DisabledButton(button)
  }

  implicit def bootstrapHtmlComponentToTag[T <: dom.Element](bc: BootstrapHtmlComponent[T]): ConcreteHtmlTag[T] = {
    bc.renderTag()
  }

  implicit def renderBootstrapComponent(bc: BootstrapComponent): Modifier = {
    bc.render()
  }

  implicit class RxVariableOps[T](value: Var[T])(implicit ctx: Ctx.Owner) {
    def reactiveRead(event: String, f: Element ⇒ T): Modifier = new Modifier {
      override def applyTo(t: Element): Unit = {
        t.asInstanceOf[js.Dynamic].addEventListener(event, js.ThisFunction.fromFunction1 { (e: Element) ⇒
          val nv = f(e)
          value() = nv
        })
      }
    }

    def reactiveReadWrite(event: String, read: Element ⇒ T, write: (Element, T) ⇒ Unit): Modifier = new Modifier {
      override def applyTo(t: Element): Unit = {
        val elRx = value.map(identity)
        elRx.foreach { v ⇒
          if (isElementAvailable(t)) {
            if (read(t) != v) write(t, v)
          } else {
            elRx.kill()
          }
        }

        val updateValue = js.ThisFunction.fromFunction1 { (e: Element) ⇒
          val nv = read(e)
          if (value.now != nv) {
            value.update(nv)
          }
        }
        t.asInstanceOf[js.Dynamic].addEventListener(event, updateValue)
      }
    }
  }

  implicit class RxValueOps[T](state: Rx[T])(implicit ctx: Ctx.Owner) {
    def reactiveWrite(f: (Element, T) ⇒ Unit): Modifier = new Modifier {
      override def applyTo(t: Element): Unit = {
        val elRx = state.map(identity)
        elRx.foreach { st ⇒
          if (isElementAvailable(t)) {
            f(t, st)
          } else {
            elRx.kill()
          }
        }
      }
    }
  }

  implicit class RxInputOps[T](value: Var[String])(implicit ctx: Ctx.Owner) {
    def reactiveInput: Modifier = {
      value.reactiveReadWrite("input", _.asInstanceOf[dom.html.Input].value, (e, v) ⇒ e.asInstanceOf[dom.html.Input].value = v)
    }
  }

  implicit class RxIntInputOps[T](value: Var[Int])(implicit ctx: Ctx.Owner) {
    def reactiveInput: Modifier = {
      value.reactiveReadWrite("input", _.asInstanceOf[dom.html.Input].value.toInt, (e, v) ⇒ e.asInstanceOf[dom.html.Input].value = v.toString)
    }
  }

  implicit class RxDoubleInputOps[T](value: Var[Double])(implicit ctx: Ctx.Owner) {
    def reactiveInput: Modifier = {
      value.reactiveReadWrite("input", _.asInstanceOf[dom.html.Input].value.toDouble, (e, v) ⇒ e.asInstanceOf[dom.html.Input].value = v.toString)
    }
  }

  implicit class RxBooleanInputOps[T](value: Var[Boolean])(implicit ctx: Ctx.Owner) {
    def reactiveInput: Modifier = {
      value.reactiveReadWrite("change", _.asInstanceOf[dom.html.Input].checked, (e, v) ⇒ e.asInstanceOf[dom.html.Input].checked = v)
    }
  }

  implicit class RxStateOps(private val state: Rx[Boolean])(implicit ctx: Ctx.Owner) {
    def reactiveShow: Modifier = {
      var oldDisplay = "block"
      state.reactiveWrite { (e, state) ⇒
        val htmlElement = e.asInstanceOf[html.Element]
        if (!state) {
          oldDisplay = htmlElement.style.display
          htmlElement.style.display = "none"
        } else if (htmlElement.style.display == "none") {
          htmlElement.style.display = oldDisplay
        }
      }
    }

    def reactiveHide: Modifier = {
      Rx(!state()).reactiveShow
    }
  }

  implicit class RxNode(rx: Rx[dom.Node])(implicit ctx: Ctx.Owner) extends Modifier {
    override def applyTo(t: Element): Unit = {
      val elRx = rx.map(identity)
      var oldElement = rx.now
      elRx.triggerLater {
        val element = oldElement
        if (isElementAvailable(element) && isElementAvailable(element.parentNode)) {
          val newElement = elRx.now
          oldElement = newElement
          element.parentNode.replaceChild(newElement, element)
        } else {
          elRx.kill()
        }
      }
      oldElement.applyTo(t)
    }
  }

  implicit class RxFragNode[T](value: Rx[T])(implicit ev: T ⇒ Frag, ctx: Ctx.Owner) extends Modifier {
    override def applyTo(t: Element): Unit = {
      new RxNode(Rx(value().render)).applyTo(t)
    }
  }

  implicit class HtmlClassOptOps(private val className: Option[String]) extends AnyVal {
    def classOpt: Modifier = new Modifier {
      override def applyTo(t: Element): Unit = {
        className.foreach(t.classList.add)
      }
    }
  }

  implicit class BootstrapIconsOps(private val iconName: String) extends AnyVal {
    def glyphicon: BootstrapGlyphicon = BootstrapGlyphicon(iconName)
    def fontAwesome(styles: FontAwesomeStyle*): FontAwesomeIcon = FontAwesome(iconName, styles:_*)
  }

  implicit def stringToBootstrapIcons(str: String): IconModifier = str.glyphicon

  //noinspection MutatorLikeMethodIsParameterless
  implicit class HtmlClassOps(private val className: String) extends AnyVal {
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

    def classIf(state: Rx[Boolean])(implicit ctx: Ctx.Owner): Modifier = state.reactiveWrite { (e, state) ⇒
      classIf(state).applyTo(e)
    }
  }

  // Reactive modifier wrapper
  final class AutoModifier(val mod: Modifier) extends AnyVal

  implicit def modifierToAutoModifier(mod: Modifier): AutoModifier = {
    new AutoModifier(mod)
  }

  implicit class AutoModifierOps(rx: Rx[AutoModifier])(implicit ctx: Ctx.Owner) extends Modifier {
    override def applyTo(t: Element): Unit = {
      val elRx = rx.map(identity)
      elRx.foreach { am ⇒
        if (isElementAvailable(t)) {
          am.mod.applyTo(t)
        } else {
          elRx.kill()
        }
      }
    }
  }

  implicit class DOMListIndexedSeq[T](dl: DOMList[T]) extends IndexedSeq[T] {
    override def length: Int = dl.length
    override def apply(idx: Int): T = dl.apply(idx)
  }

  @inline
  private def isElementAvailable(e: dom.Node): Boolean = {
    !js.isUndefined(e) && e.ne(null)
  }
}
