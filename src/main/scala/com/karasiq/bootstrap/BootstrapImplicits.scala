package com.karasiq.bootstrap

import com.karasiq.bootstrap.buttons._
import com.karasiq.bootstrap.icons.{BootstrapGlyphicon, FontAwesome, FontAwesomeIcon, IconModifier}
import org.scalajs.dom
import org.scalajs.dom.Element
import org.scalajs.dom.raw.{File, FileList}
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

  implicit class ButtonOps(val button: HtmlButton) extends AnyVal {
    def toggleButton: ToggleButton = new ToggleButton(button)
    def disabledButton: DisabledButton = new DisabledButton(button)
  }

  implicit def bootstrapHtmlComponentToTag[T <: dom.Element](bc: BootstrapHtmlComponent[T]): ConcreteHtmlTag[T] = {
    bc.renderTag()
  }

  implicit def renderBootstrapComponent(bc: BootstrapComponent): Modifier = {
    bc.render()
  }

  implicit class RxVariableOps[T](value: Var[T]) {
    def reactiveRead(event: String, f: Element ⇒ T): Modifier = new Modifier {
      override def applyTo(t: Element): Unit = {
        t.asInstanceOf[js.Dynamic].addEventListener(event, js.ThisFunction.fromFunction1 { (e: Element) ⇒
          val nv = f(e)
          if (value.now != nv) {
            value.update(nv)
          }
        })
      }
    }

    def reactiveReadWrite(event: String, read: Element ⇒ T, write: (Element, T) ⇒ Unit): Modifier = new Modifier {
      override def applyTo(t: Element): Unit = {
        val observer = value.foreach { v ⇒
          write(t, v)
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

  implicit class RxValueOps[T](state: Rx[T]) {
    def reactiveWrite(f: (dom.Element, T) ⇒ Unit): Modifier = new Modifier {
      override def applyTo(t: Element): Unit = {
        state.foreach { st ⇒
          f(t, st)
        }
      }
    }
  }

  implicit class RxInputOps[T](value: Var[String]) {
    def reactiveInput: Modifier = {
      value.reactiveReadWrite("input", _.asInstanceOf[dom.html.Input].value, (e, v) ⇒ e.asInstanceOf[dom.html.Input].value = v)
    }
  }

  implicit class RxIntInputOps[T](value: Var[Int]) {
    def reactiveInput: Modifier = {
      value.reactiveReadWrite("input", _.asInstanceOf[dom.html.Input].value.toInt, (e, v) ⇒ e.asInstanceOf[dom.html.Input].value = v.toString)
    }
  }

  implicit class RxDoubleInputOps[T](value: Var[Double]) {
    def reactiveInput: Modifier = {
      value.reactiveReadWrite("input", _.asInstanceOf[dom.html.Input].value.toDouble, (e, v) ⇒ e.asInstanceOf[dom.html.Input].value = v.toString)
    }
  }

  implicit class RxBooleanInputOps[T](value: Var[Boolean]) {
    def reactiveInput: Modifier = {
      value.reactiveReadWrite("change", _.asInstanceOf[dom.html.Input].checked, (e, v) ⇒ e.asInstanceOf[dom.html.Input].checked = v)
    }
  }

  implicit class RxStateOps(val state: Rx[Boolean])(implicit ctx: Ctx.Owner) {
    def reactiveShow: Modifier = {
      val oldDisplay = Var("block")
      state.reactiveWrite { (e, state) ⇒
        if (!state) {
          oldDisplay.update(e.asInstanceOf[dom.html.Element].style.display)
          e.asInstanceOf[dom.html.Element].style.display = "none"
        } else if (e.asInstanceOf[dom.html.Element].style.display == "none") {
          e.asInstanceOf[dom.html.Element].style.display = oldDisplay.now
        }
      }
    }

    def reactiveHide: Modifier = {
      Rx(!state()).reactiveShow
    }
  }

  implicit class RxNode(rx: Rx[dom.Node])(implicit ctx: Ctx.Owner) extends Modifier {
    override def applyTo(t: Element): Unit = {
      val container = Var(rx.now)
      val obs: Obs = rx.triggerLater {
        val element = container.now
        val newElement = rx.now
        container.update(newElement)
        element.parentNode match {
          case node if node != null && !js.isUndefined(node) ⇒
            node.replaceChild(newElement, element)

          case _ ⇒
            // Skip
        }
      }
      container.now.applyTo(t)
    }
  }

  implicit class RxFragNode[T](value: Rx[T])(implicit ev: T ⇒ Frag, ctx: Ctx.Owner) extends Modifier {
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

  implicit class BootstrapIconsOps(val iconName: String) extends AnyVal {
    def glyphicon: BootstrapGlyphicon = BootstrapGlyphicon(iconName)
    def fontAwesome(styles: Modifier*): FontAwesomeIcon = FontAwesome(iconName, styles:_*)
  }

  implicit def stringToBootstrapIcons(str: String): IconModifier = str.glyphicon

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

    def classIf(state: Rx[Boolean]): Modifier = state.reactiveWrite { (e, state) ⇒
      classIf(state).applyTo(e)
    }
  }

  // Reactive modifier
  final class AutoModifier(val mod: Modifier) extends AnyVal

  implicit def modifierToRxModifier(mod: Modifier): AutoModifier = {
    new AutoModifier(mod)
  }

  implicit class AutoModifierOps(rx: Rx[AutoModifier])(implicit ctx: Ctx.Owner) extends Modifier {
    override def applyTo(t: Element): Unit = {
      rx.foreach(_.mod.applyTo(t))
    }
  }

  implicit def fileListToSeq(fl: FileList): Seq[File] = {
    for (i <- 0 until fl.length) yield fl(i)
  }
}
