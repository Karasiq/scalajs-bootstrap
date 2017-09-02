package com.karasiq.bootstrap.form

import scala.language.{implicitConversions, postfixOps}

import rx.{Rx, Var}

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap.utils.{ClassModifiers, Utils}

trait UniversalForms { self: RenderingContext with Forms with Utils with BootstrapComponents with ClassModifiers ⇒
  import scalaTags.all._

  object Form extends FormFactory {
    def apply(md: Modifier*): FormT = {
      form(md)
    }

    def inline(md: Modifier*): InlineFormT = {
      form("form-inline".addClass, md)
    }

    def submit(text: Modifier): SubmitT = {
      button(`type` := "submit", Seq("btn", "btn-default").map(_.addClass), text)
    }
  }

  object FormInput extends FormInputFactory {
    def ofType(inputType: String, label: Modifier, md: Modifier*): Tag = {
      new FormGenericInput(label, inputType).renderTag(md:_*)
    }

    def file(label: Modifier, md: Modifier*): InputT = {
      this.ofType("file", label, "form-control".removeClass, md)
    }

    def textArea(title: Modifier, md: Modifier*): InputT = {
      new FormTextArea(title).renderTag(md:_*)
    }

    def checkbox(label: Modifier, md: Modifier*): InputT = {
      new FormCheckbox(label).renderTag(md:_*)
    }

    def radio(title: Modifier, radioName: String, radioValue: String,
              initialState: Boolean = false, radioId: String = Bootstrap.newId): FormRadio = {
      new FormRadio(title, radioName, radioValue, initialState, radioId)
    }

    def radioGroup[T <: RadioT](radios: Rx[Seq[T]]): FormRadioGroup = {
      new FormRadioGroup(radios)
    }

    def select(title: Modifier, options: FormSelectOptions): FormSelect = {
      new FormSelect(title, false, options)
    }

    def multipleSelect(title: Modifier, options: FormSelectOptions): FormSelect = {
      new FormSelect(title, true, options)
    }
  }


  object FormInputGroup extends FormInputGroupFactory {
    def createInput(tpe: String, md: Modifier*): InputT = {
      input(`type` := tpe, "form-control".addClass, id := Bootstrap.newId, md)
    }

    def addon(md: Modifier*): AddonT = {
      div("input-group-addon".addClass, md)
    }

    def apply(label: Modifier, md: Modifier*): GroupT = {
      div("form-group".addClass, label, div("input-group".addClass, md))
    }
  }

  final class FormGenericInput(val inputLabel: Modifier,
                               val inputType: String = "text",
                               val inputId: String = Bootstrap.newId)
    extends BootstrapHtmlComponent {

    override def renderTag(md: Modifier*): Tag = {
      val controlId = s"$inputId-form-$inputType-input"
      div("form-group".addClass)(
        label(`for` := controlId, inputLabel),
        input("form-control".addClass, `type` := inputType, id := controlId, md)
      )
    }
  }

  final class FormCheckbox(val inputLabel: Modifier,
                           val inputId: String = Bootstrap.newId)
    extends AbstractFormInput with BootstrapHtmlComponent {

    val inputType = "checkbox"

    override def renderTag(md: Modifier*): Tag = {
      div("checkbox".addClass)(
        label(
          input(`type` := "checkbox", id := inputId, md),
          Bootstrap.nbsp,
          inputLabel
        )
      )
    }
  }

  class FormRadio(val title: Modifier, val radioName: String, val radioValue: String,
                  val isDefaultOption: Boolean = false, val radioId: String = Bootstrap.newId)
    extends AbstractFormRadio with BootstrapHtmlComponent {

    override def renderTag(md: ModifierT*): TagT = {
      div("radio".addClass)(
        label(
          input(`type` := "radio", name := radioName, id := radioId, value := radioValue, md),
          title
        )
      )
    }
  }

  class FormRadioGroup(final val radioList: Rx[Seq[AbstractFormRadio]],
                       val inputId: String = Bootstrap.newId) extends AbstractFormRadioGroup {

    final val value: Var[String] = Var(initialValue.getOrElse(""))

    private[this] def initialValue = {
      radioList.now.find(_.isDefaultOption)
        .orElse(radioList.now.headOption)
        .map(_.radioValue)
    }

    private[this] def valueBind(radio: AbstractFormRadio): ModifierT = new ModifierT {
      def applyTo(element: Element): Unit = {
        val radioValue = Var(radio.isDefaultOption)
        value.foreach(value ⇒ radioValue() = value == radio.radioValue)
        radioValue.foreach(checked ⇒ if (checked) value() = radio.radioValue)
        radioValue.reactiveInput.applyTo(element)
      }
    }

    def renderTag(md: ModifierT*): TagT = {
      div(Rx {
        val radios = radioList()
        div(radios.map(radio ⇒ radio.renderTag(md, valueBind(radio))))
      })
    }
  }

  class FormSelect(selectLabel: Modifier,
                   val allowMultiple: Boolean,
                   val options: FormSelectOptions,
                   val inputId: String = Bootstrap.newId)
                   extends AbstractFormSelect {

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

  class FormTextArea(val textAreaLabel: Modifier, val inputId: String = Bootstrap.newId) extends BootstrapHtmlComponent {
    override def renderTag(md: Modifier*): Tag = {
      val controlId = s"$inputId-form-textarea-input"
      div("form-group".addClass)(
        label(`for` := controlId, textAreaLabel),
        textarea("form-control".addClass, id := controlId, md)
      )
    }
  }
}

