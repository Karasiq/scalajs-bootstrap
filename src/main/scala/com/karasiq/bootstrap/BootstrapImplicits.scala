package com.karasiq.bootstrap

import com.karasiq.bootstrap.buttons._
import org.scalajs.dom
import org.scalajs.dom.Element
import rx.{Obs, Rx, Var}

import scala.language.implicitConversions
import scalatags.JsDom.all._

object BootstrapImplicits {
  private type HtmlButton = ConcreteHtmlTag[dom.html.Button]

  implicit class ButtonOps(val button: HtmlButton) extends AnyVal {
    def toggleButton: ToggleButton = new ToggleButton(button)
    def disabledButton: DisabledButton = new DisabledButton(button)
  }

  implicit def buttonBuilderToButton(btn: ButtonBuilder): HtmlButton = {
    btn.build
  }

  implicit def buttonGroupToTag(btnGroup: ButtonGroup): Tag = {
    div(`class` := (Seq("btn-group") ++ btnGroup.size.sizeClass).mkString(" "), role := "group", aria.label := "Button group")(
      btnGroup.buttons
    )
  }

  implicit def buttonToolbarToTag(btnToolbar: ButtonToolbar): Tag = {
    div(`class` := "btn-toolbar", role := "toolbar", aria.label := "Button toolbar")(
      btnToolbar.buttonGroups.map(buttonGroupToTag)
    )
  }

  implicit def btnWrapperToButtonTag[B](btnWrapper: B)(implicit ev: B <:< ButtonWrapper): dom.html.Button = {
    btnWrapper.button
  }

  implicit def bindRxAttr[T](implicit ev: AttrValue[T]): AttrValue[Rx[T]] = new AttrValue[Rx[T]] {
    override def apply(t: Element, a: Attr, v: Rx[T]): Unit = {
      Obs(v, "rx-attr-updater") {
        ev.apply(t, a, v())
      }
    }
  }

  implicit class RxElementNode(rx: Rx[Element]) extends Modifier {
    override def applyTo(t: Element): Unit = {
      val container = Var(rx.now)
      Obs(rx, "rx-dom-updater", skipInitial = true) {
        val element = container()
        val newElement = rx()
        container.updateSilent(newElement)
        element.parentNode.replaceChild(newElement, element)
      }
      container.now.applyTo(t)
    }
  }

  implicit class RxTagNode(rx: Rx[Tag]) extends Modifier {
    override def applyTo(t: Element): Unit = {
      new RxElementNode(Rx(rx().render)).applyTo(t)
    }
  }

  implicit class RxModifier[A](rx: Rx[A])(implicit ev: A ⇒ Modifier) extends Modifier {
    override def applyTo(t: Element): Unit = {
      Obs(rx, "rx-modifier-apply") {
        rx().applyTo(t)
      }
    }
  }
}
