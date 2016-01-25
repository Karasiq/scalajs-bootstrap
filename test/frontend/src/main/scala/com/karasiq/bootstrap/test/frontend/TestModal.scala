package com.karasiq.bootstrap.test.frontend

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.form.{Form, FormInput}
import com.karasiq.bootstrap.modal.Modal
import rx._

import scalatags.JsDom.all._

object TestModal {
  def apply()(implicit ctx: Ctx.Owner): Modal = {
    val modalInputValue = Var("10000000") // Better use string
    val radioGroup = FormInput.radioGroup(FormInput.radio("Test1", "modal-title", "First radio"), FormInput.radio("Test2", "modal-title", "Second radio"))
    val select = FormInput.select("Plain select", "Option 1", "Option 2", "Option 3")
    val multipleSelect = FormInput.multipleSelect("Multiple select", "Option 1", "Option 2", "Option 3")
    val form = Form(
      FormInput.number("Money", modalInputValue.reactiveInput),
      radioGroup,
      select,
      multipleSelect,
      FormInput.textArea("Money text area", rows := 1, modalInputValue.reactiveInput)
    )

    Modal()
      .withTitle(radioGroup.value, " / ", select.selected.map(_.head), " / ", multipleSelect.selected.map(_.mkString(" + ")))
      .withBody(p("You won ", modalInputValue, "$"), p(form))
      .withButtons(Modal.closeButton(), Modal.button("Take", Modal.dismiss))
  }
}
