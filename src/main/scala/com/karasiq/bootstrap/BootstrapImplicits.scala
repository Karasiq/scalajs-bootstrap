package com.karasiq.bootstrap

import com.karasiq.bootstrap.buttons._
import org.scalajs.dom

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
}
