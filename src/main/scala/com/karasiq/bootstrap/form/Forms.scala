package com.karasiq.bootstrap.form

import com.karasiq.bootstrap.context.BootstrapBundle
import rx.{Rx, Var}

import scala.language.{implicitConversions, postfixOps}

trait Forms { self: BootstrapBundle ⇒
  import scalaTags.all._

  object Form {
    def submit(text: Modifier): Tag = {
      button(`type` := "submit", Seq("btn", "btn-default").map(_.addClass), text)
    }

    def apply(md: Modifier*): Tag = {
      form(md)
    }

    def inline(md: Modifier*): Tag = {
      form("form-inline".addClass, md)
    }
  }

  final class FormGenericInput(formLabel: Modifier, inputType: String = "text", inputId: String = Bootstrap.newId) extends BootstrapHtmlComponent {
    override def renderTag(md: Modifier*): Tag = {
      val controlId = s"$inputId-form-$inputType-input"
      div("form-group".addClass)(
        label(`for` := controlId, formLabel),
        input("form-control".addClass, `type` := inputType, id := controlId, md)
      )
    }
  }

  final class FormCheckbox(checkboxLabel: Modifier) extends BootstrapHtmlComponent {
    override def renderTag(md: Modifier*): Tag = {
      div("checkbox".addClass)(
        label(
          input(`type` := "checkbox", md),
          raw("&nbsp;"),
          checkboxLabel
        )
      )
    }
  }

  class FormRadio(val title: Modifier, val radioName: String, val radioValue: String,
                  val initialChecked: Boolean = false, val radioId: String = Bootstrap.newId) extends BootstrapHtmlComponent {
    override def renderTag(md: Modifier*): Tag = {
      div("radio".addClass)(
        label(
          input(`type` := "radio", name := radioName, id := radioId, value := radioValue, md),
          title
        )
      )
    }
  }

  class FormRadioGroup(final val radioList: Rx[Seq[FormRadio]]) extends BootstrapComponent {
    final val value: Var[String] = Var(initialValue.getOrElse(""))

    private[this] def initialValue = {
      radioList.now.find(_.initialChecked)
        .orElse(radioList.now.headOption)
        .map(_.radioValue)
    }

    private[this] def valueBind(radio: FormRadio): Modifier = new Modifier {
      def applyTo(element: Element): Unit = {
        val radioValue = Var(radio.initialChecked)
        value.foreach(value ⇒ radioValue() = value == radio.radioValue)
        radioValue.foreach(checked ⇒ if (checked) value() = radio.radioValue)
        radioValue.reactiveInput.applyTo(element)
      }
    }

    override def render(md: Modifier*): Modifier = {
      Rx {
        val radios = radioList()
        div(radios.map(radio ⇒ radio.renderTag(md, valueBind(radio))))
      }
    }
  }

  class FormSelect(selectLabel: Modifier, allowMultiple: Boolean,
                   val options: FormSelectOptions,
                   val inputId: String = Bootstrap.newId)
                   extends BootstrapHtmlComponent {
    final val selected: Var[Seq[String]] = Var(options.values.now.headOption.map(_.value).toSeq)

    override def renderTag(md: Modifier*): Tag = {
      val controlId = s"$inputId-form-select-input"
      div("form-group".addClass)(
        label(`for` := controlId, selectLabel),
        Rx {
          select("form-control".addClass, if (allowMultiple) multiple else (), id := controlId, md)(
            for (FormSelectOption(optValue, title) ← options.values()) yield option(title, value := optValue),
            selected.reactiveInput
          )
        }
      )
    }
  }

  case class FormSelectOption(value: String, title: Modifier)
  final class FormSelectOptions(val values: Rx[Seq[FormSelectOption]])

  object FormSelectOptions {
    implicit def fromStaticValues(values: Seq[FormSelectOption]): FormSelectOptions = new FormSelectOptions(Var(values))
    implicit def fromRxValues(values: Rx[Seq[FormSelectOption]]): FormSelectOptions = new FormSelectOptions(values)
  }

  class FormTextArea(val textAreaLabel: Modifier, val inputId: String = Bootstrap.newId) extends BootstrapHtmlComponent {
    override def renderTag(md: Modifier*): Tag = {
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

    def file(label: Modifier, md: Modifier*): Tag = this.ofType("file", label, "form-control".removeClass, md)

    def textArea(title: Modifier, md: Modifier*): Tag = {
      new FormTextArea(title).renderTag(md:_*)
    }

    def checkbox(label: Modifier, md: Modifier*): Tag = {
      new FormCheckbox(label).renderTag(md:_*)
    }

    def radio(title: Modifier, radioName: String, radioValue: String, initialState: Boolean = false, radioId: String = Bootstrap.newId): FormRadio = {
      new FormRadio(title, radioName, radioValue, initialState, radioId)
    }

    def radioGroup(radios: FormRadio*): FormRadioGroup = {
      new FormRadioGroup(Rx(radios))
    }

    def radioGroup(radios: Rx[Seq[FormRadio]]): FormRadioGroup = {
      new FormRadioGroup(radios)
    }

    def select(title: Modifier, options: (String, Modifier)*): FormSelect = {
      new FormSelect(title, false, options.map(FormSelectOption.tupled))
    }

    def simpleSelect(title: Modifier, options: String*): FormSelect = {
      new FormSelect(title, false, options.map(opt ⇒ FormSelectOption(opt, opt)))
    }

    def select(title: Modifier, options: FormSelectOptions): FormSelect = {
      new FormSelect(title, false, options)
    }

    def multipleSelect(title: Modifier, options: (String, Modifier)*): FormSelect = {
      new FormSelect(title, true, options.map(FormSelectOption.tupled))
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


  object FormInputGroup {
    def createInput(tpe: String, md: Modifier*): Tag = {
      input(`type` := tpe, "form-control".addClass, id := Bootstrap.newId, md)
    }

    def label(md: Modifier*): Tag = scalaTags.tags.label(md)
    def text(md: Modifier*): Tag = this.createInput("text", md:_*)
    def number(md: Modifier*): Tag = this.createInput("number", md:_*)
    def email(md: Modifier*): Tag = this.createInput("email", md:_*)
    def password(md: Modifier*): Tag = this.createInput("password", md:_*)
    def addon(md: Modifier*): Tag = div("input-group-addon".addClass, md)

    def apply(label: Modifier, md: Modifier*): Tag = {
      div("form-group".addClass, label, div("input-group".addClass, md))
    }
  }
}

