package com.karasiq.bootstrap_text.form

import com.karasiq.bootstrap_text.BootstrapImplicits._
import com.karasiq.bootstrap_text.{Bootstrap, BootstrapComponent, BootstrapHtmlComponent}

import scala.language.implicitConversions
import scalatags.Text.all._

final class FormGenericInput(formLabel: Modifier, inputType: String = "text", inputId: String = Bootstrap.newId) extends BootstrapHtmlComponent {
  override def renderTag(md: Modifier*): RenderedTag = {
    val controlId = s"$inputId-form-$inputType-input"
    div("form-group".addClass)(
      label(`for` := controlId, formLabel),
      input("form-control".addClass, `type` := inputType, id := controlId, md)
    )
  }
}

final class FormCheckbox(checkboxLabel: Modifier) extends BootstrapHtmlComponent {
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

class FormRadio(title: Modifier, val radioName: String, val radioValue: String, val radioId: String = Bootstrap.newId) extends BootstrapHtmlComponent {
  override def renderTag(md: Modifier*): RenderedTag = {
    div("radio".addClass)(
      label(
        input(`type` := "radio", name := radioName, id := radioId, value := radioValue, md),
        title
      )
    )
  }
}

class FormRadioGroup(final val radioList: Seq[FormRadio]) extends BootstrapComponent {
  def value: String = radioList.head.radioValue

  override def render(md: Modifier*): Modifier = {
    div(radioList.head.renderTag(md, checked), radioList.tail.map(_.renderTag(md)))
  }
}

class FormSelect(selectLabel: Modifier, allowMultiple: Boolean, final val options: FormSelectOptions, val inputId: String = Bootstrap.newId) extends BootstrapHtmlComponent {
  final val selected: Seq[String] = options.values.headOption.map(_.value).toSeq

  override def renderTag(md: Modifier*): RenderedTag = {
    val controlId = s"$inputId-form-select-input"
    div("form-group".addClass)(
      label(`for` := controlId, selectLabel),
      select("form-control".addClass, if (allowMultiple) multiple else (), id := controlId, md)(
        for (FormSelectOption(optValue, title) ← options.values) yield option(title, value := optValue)
      )
    )
  }
}

case class FormSelectOption(value: String, title: Modifier)
final class FormSelectOptions(val values: Seq[FormSelectOption]) extends AnyVal 

object FormSelectOptions {
  implicit def fromStaticValues(values: Seq[FormSelectOption]): FormSelectOptions = new FormSelectOptions(values)
}

class FormTextArea(val textAreaLabel: Modifier, val inputId: String = Bootstrap.newId) extends BootstrapHtmlComponent {
  override def renderTag(md: Modifier*): RenderedTag = {
    val controlId = s"$inputId-form-textarea-input"
    div("form-group".addClass)(
      label(`for` := controlId, textAreaLabel),
      textarea("form-control".addClass, id := controlId, md)
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

  def file(label: Modifier, md: Modifier*): Tag = this.ofType("file", label, "form-control".removeClass +: md:_*)

  def textArea(title: Modifier, md: Modifier*): Tag = {
    new FormTextArea(title).renderTag(md:_*)
  }

  def checkbox(label: Modifier, md: Modifier*): Tag = {
    new FormCheckbox(label).renderTag(md:_*)
  }

  def radio(title: Modifier, radioName: String, radioValue: String, radioId: String = Bootstrap.newId): FormRadio = {
    new FormRadio(title, radioName, radioValue, radioId)
  }

  def radioGroup(radios: FormRadio*): FormRadioGroup = {
    new FormRadioGroup(radios)
  }

  def simpleSelect(title: Modifier, options: String*): FormSelect = {
    new FormSelect(title, false, options.map(opt ⇒ FormSelectOption(opt, opt)))
  }

  def select(title: Modifier, options: FormSelectOptions): FormSelect = {
    new FormSelect(title, false, options)
  }

  def simpleMultipleSelect(title: Modifier, options: String*): FormSelect = {
    new FormSelect(title, true, options.map(opt ⇒ FormSelectOption(opt, opt)))
  }

  def multipleSelect(title: Modifier, options: FormSelectOptions): FormSelect = {
    new FormSelect(title, true, options)
  }

  // Default
  def apply(label: Modifier, md: Modifier*): Tag = this.text(label, md:_*)
}
