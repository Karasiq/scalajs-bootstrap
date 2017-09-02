package com.karasiq.bootstrap.modal

import com.karasiq.bootstrap.context.RenderingContext

trait ModalStyles { self: RenderingContext ⇒
  import scalaTags.all._

  sealed trait ModalDialogSize extends ModifierFactory

  object DefaultModalDialogSize extends ModalDialogSize {
    val createModifier: Modifier = ()
  }

  final class CustomModalDialogSize private[modal](size: String) extends ModalDialogSize {
    val className: String = size
    val createModifier: Modifier = className.addClass
  }

  //noinspection TypeAnnotation
  object ModalDialogSize {
    val default = DefaultModalDialogSize
    val small = new CustomModalDialogSize("modal-sm")
    val large = new CustomModalDialogSize("modal-lg")
  }
}
