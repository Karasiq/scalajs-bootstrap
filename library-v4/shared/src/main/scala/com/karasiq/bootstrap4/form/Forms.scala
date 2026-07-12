package com.karasiq.bootstrap4.form

import scala.language.{implicitConversions, postfixOps}

import rx.{Rx, Var}

import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap4.utils.Utils

trait Forms { self: RenderingContext with Utils ⇒
  import scalaTags.all._

  val Form: FormFactory
  val FormInput: FormInputFactory
  val FormInputGroup: FormInputGroupFactory

  case class FormSelectOption(value: String, title: Modifier)
  final class FormSelectOptions(val values: Rx[Seq[FormSelectOption]])

  object FormSelectOptions {
    implicit def fromStaticValues(values: Seq[FormSelectOption]): FormSelectOptions = new FormSelectOptions(Var(values))
    implicit def fromRxValues(values: Rx[Seq[FormSelectOption]]): FormSelectOptions = new FormSelectOptions(values)
  }

  trait FormFactory {
    type FormT       = Tag
    type InlineFormT = Tag
    type SubmitT     = Tag

    def apply(md: Modifier*): FormT
    def inline(md: Modifier*): InlineFormT

    def submit(md: Modifier*): SubmitT
  }

  trait AbstractFormInput extends BootstrapHtmlComponent {
    def inputId: String
    def inputType: String
  }

  trait AbstractFormSelect extends AbstractFormInput {
    def allowMultiple: Boolean
    def options: FormSelectOptions
    def selected: Var[Seq[String]]

    def inputType: String = "select"
  }

  trait AbstractFormRadio extends AbstractFormInput {
    def radioId: String
    def radioName: String
    def radioValue: String
    def isDefaultOption: Boolean

    def inputType: String = "radio"
    def inputId: String   = this.radioId
  }

  trait AbstractFormRadioGroup extends AbstractFormInput {
    def radioList: Rx[Seq[AbstractFormRadio]]
    def value: Var[String]

    def inputType: String = "radiogroup"
  }

  trait FormInputFactory {
    type InputT      = Tag
    type RadioT      = AbstractFormRadio
    type RadioGroupT = AbstractFormRadioGroup
    type SelectT     = AbstractFormSelect

    def ofType(inputType: String, label: Modifier, md: Modifier*): InputT
    def text(label: Modifier, md: Modifier*): Tag     = this.ofType("text", label, md: _*)
    def number(label: Modifier, md: Modifier*): Tag   = this.ofType("number", label, md: _*)
    def email(label: Modifier, md: Modifier*): Tag    = this.ofType("email", label, md: _*)
    def password(label: Modifier, md: Modifier*): Tag = this.ofType("password", label, md: _*)
    def file(label: Modifier, md: Modifier*): Tag
    def textArea(title: Modifier, md: Modifier*): InputT
    def checkbox(label: Modifier, md: Modifier*): InputT

    def radio(
        title: Modifier,
        radioName: String,
        radioValue: String,
        initialState: Boolean = false,
        radioId: String = Bootstrap.newId
    ): RadioT

    def radioGroup[T <: RadioT](radios: Rx[Seq[T]]): RadioGroupT

    def radioGroup[T <: RadioT](radios: T*): RadioGroupT = {
      radioGroup(Var(radios))
    }

    def select(title: Modifier, options: FormSelectOptions): SelectT

    def select(title: Modifier, options: (String, Modifier)*): SelectT = {
      select(title, options.map(FormSelectOption.tupled))
    }

    def simpleSelect(title: Modifier, options: String*): SelectT = {
      select(title, options.map(opt ⇒ FormSelectOption(opt, opt)))
    }

    def multipleSelect(title: Modifier, options: FormSelectOptions): SelectT

    def multipleSelect(title: Modifier, options: (String, Modifier)*): SelectT = {
      multipleSelect(title, options.map(FormSelectOption.tupled))
    }

    def simpleMultipleSelect(title: Modifier, options: String*): SelectT = {
      multipleSelect(title, options.map(opt ⇒ FormSelectOption(opt, opt)))
    }

    // Default
    def apply(label: Modifier, md: Modifier*): Tag = this.text(label, md: _*)
  }

  trait FormInputGroupFactory {
    type InputT = Tag
    type AddonT = Tag
    type GroupT = Tag

    def createInput(inputType: String, md: Modifier*): InputT
    def addon(md: Modifier*): AddonT
    def apply(label: Modifier, md: Modifier*): GroupT

    def label(md: Modifier*): InputT    = scalaTags.tags.label(md)
    def text(md: Modifier*): InputT     = this.createInput("text", md: _*)
    def number(md: Modifier*): InputT   = this.createInput("number", md: _*)
    def email(md: Modifier*): InputT    = this.createInput("email", md: _*)
    def password(md: Modifier*): InputT = this.createInput("password", md: _*)
  }
}
