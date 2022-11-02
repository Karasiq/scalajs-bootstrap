package com.karasiq.bootstrap4.modal

import com.karasiq.bootstrap.context.RenderingContext
import com.karasiq.bootstrap4.utils.Utils

trait ModalStyles { self: RenderingContext with Utils ⇒
  import scalaTags.all._

  sealed trait ModalDialogSize extends ModifierFactory

  object DefaultModalDialogSize extends ModalDialogSize {
    val createModifier = Bootstrap.noModifier
  }

  final class CustomModalDialogSize private[modal] (size: String) extends ModalDialogSize {
    val className: String = size
    val createModifier    = className.addClass
  }

  // noinspection TypeAnnotation
  object ModalDialogSize {
    val default = DefaultModalDialogSize
    val small   = new CustomModalDialogSize("modal-sm")
    val large   = new CustomModalDialogSize("modal-lg")
  }
}
