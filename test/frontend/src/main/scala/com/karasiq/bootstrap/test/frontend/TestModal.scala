package com.karasiq.bootstrap.test.frontend

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.form.{Form, FormInput}
import com.karasiq.bootstrap.modal.Modal
import rx._

import scalatags.JsDom.all._

object TestModal {
  def apply(): Modal = {
    val modalInputValue = Var("10000000") // Better use string
    val radioGroup = FormInput.radioGroup(FormInput.radio("Test1", "modal-title", "First radio selected"), FormInput.radio("Test2", "modal-title", "Second radio selected"))
    val form = Form(
      FormInput.number("Money", modalInputValue.reactiveInput),
      radioGroup
    )
    Modal(radioGroup.value)
      .withBody(p("You won ", modalInputValue, "$"), p(form))
      .withButtons(Modal.closeButton(), Modal.button("Take", Modal.dismiss))
  }
}
