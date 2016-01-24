package com.karasiq.bootstrap.form

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.{Bootstrap, BootstrapComponent, BootstrapHtmlComponent}
import org.scalajs.dom
import rx._

import scalatags.JsDom.all._

class FormGenericInput(formLabel: Modifier, inputType: String = "text", inputId: String = Bootstrap.newId) extends BootstrapHtmlComponent[dom.html.Div] {
  override def renderTag(md: Modifier*): RenderedTag = {
    val controlId = s"$inputId-form-$inputType-input"
    div("form-group".addClass)(
      label(`for` := controlId, formLabel),
      input("form-control".addClass, `type` := inputType, id := controlId, md)
    )
  }
}

class FormCheckbox(checkboxLabel: Modifier) extends BootstrapHtmlComponent[dom.html.Div] {
  override def renderTag(md: Modifier*): RenderedTag = {
    div("checkbox".addClass)(
      label(
        input(`type` := "checkbox", md),
        raw("&nbsp;"),
        checkboxLabel
      )
    )
  }
}

class FormRadio(val title: String, val radioName: String, val radioValue: String, val radioId: String = Bootstrap.newId) extends BootstrapHtmlComponent[dom.html.Div] {
  override def renderTag(md: Modifier*): RenderedTag = {
    div("radio".addClass)(
      label(
        input(`type` := "radio", name := radioName, id := radioId, value := radioValue, md),
        title
      )
    )
  }
}

class FormRadioGroup(values: FormRadio*) extends BootstrapComponent {
  require(values.forall(_.radioName == values.head.radioName), "Invalid radio group")

  final val value: Var[String] = Var(values.head.radioValue)

  private def valueWriter(r: FormRadio) = value.reactiveReadWrite("change", e ⇒ {
    val input = e.asInstanceOf[dom.html.Input]
    if (input.checked) {
      r.radioValue
    } else {
      value.now
    }
  }, (e, v) ⇒ {
    val input = e.asInstanceOf[dom.html.Input]
    input.checked = v == r.radioValue
  })

  override def render(md: Modifier*): Modifier = {
    Seq[Modifier](
      values.head.renderTag(md, checked, valueWriter(values.head)),
      values.tail.map(v ⇒ v.renderTag(md, valueWriter(v)))
    )
  }
}

object FormInput {
  def ofType(tpe: String, label: Modifier, md: Modifier*): Tag = {
    new FormGenericInput(label, tpe).renderTag(md:_*)
  }

  def text(label: Modifier, md: Modifier*): Tag = this.ofType("text", label, md:_*)

  def number(label: Modifier, md: Modifier*): Tag = this.ofType("number", label, md:_*)

  def email(label: Modifier, md: Modifier*): Tag = this.ofType("email", label, md:_*)

  def password(label: Modifier, md: Modifier*): Tag = this.ofType("password", label, md:_*)

  def file(label: Modifier, md: Modifier*): Tag = this.ofType("file", label, md:_*)

  def checkbox(label: Modifier, md: Modifier*): Tag = {
    new FormCheckbox(label).renderTag(md:_*)
  }

  def radio(title: String, radioName: String, radioValue: String, radioId: String = Bootstrap.newId): FormRadio = {
    new FormRadio(title, radioName, radioValue, radioId)
  }

  def radioGroup(radios: FormRadio*): FormRadioGroup = {
    new FormRadioGroup(radios:_*)
  }

  // Default
  def apply(label: Modifier, md: Modifier*): Tag = this.text(label, md:_*)
}
