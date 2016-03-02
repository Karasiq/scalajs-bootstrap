package com.karasiq.bootstrap.buttons

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.{Bootstrap, BootstrapHtmlComponent}
import org.scalajs.dom
import rx._

import scalatags.JsDom.all._

final class DisabledButton(btn: ConcreteHtmlTag[dom.html.Button])(implicit ctx: Ctx.Owner) extends BootstrapHtmlComponent[dom.html.Button] {
  val state: Var[Boolean] = Var(false)

  override def renderTag(md: Modifier*): RenderedTag = {
    btn(
      "disabled".classIf(state),
      onclick := Bootstrap.jsClick { _ ⇒
        if (!state.now) state.update(true)
      },
      md
    )
  }
}
