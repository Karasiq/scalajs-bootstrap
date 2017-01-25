package com.karasiq.bootstrap.modal

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ModifierFactory

import scala.language.postfixOps
import scalatags.JsDom.all._

sealed trait ModalDialogSize extends ModifierFactory

object DefaultModalDialogSize extends ModalDialogSize {
  val createModifier: Modifier = ()
}

final class CustomModalDialogSize private[modal](size: String) extends ModalDialogSize {
  val className: String = size
  val createModifier: Modifier = className.addClass
}

object ModalDialogSize {
  def default = DefaultModalDialogSize
  val small = new CustomModalDialogSize("modal-sm")
  val large = new CustomModalDialogSize("modal-lg")
}