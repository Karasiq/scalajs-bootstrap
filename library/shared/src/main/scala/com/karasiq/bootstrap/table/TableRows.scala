package com.karasiq.bootstrap.table

import com.karasiq.bootstrap.context.{ClassModifiers, RenderingContext}
import com.karasiq.bootstrap.utils.Utils

trait TableRows { self: RenderingContext with ClassModifiers with Utils ⇒
  import scalaTags.all._

  case class TableRow(columns: Seq[Modifier], modifiers: Modifier*)

  sealed trait TableRowStyle extends ModifierFactory {
    def styleClass: Option[String]
    override final def createModifier: ModifierT = styleClass.map(_.addClass)
  }

  // noinspection TypeAnnotation
  object TableRowStyle {
    case object Default extends TableRowStyle {
      val styleClass = None
    }

    case class Styled(styleName: String) extends TableRowStyle with StyleClassModifier {
      val styleClass = Some(styleName)
      val className  = styleName
    }

    def default      = Default
    lazy val active  = Styled("active")
    lazy val success = Styled("success")
    lazy val warning = Styled("warning")
    lazy val danger  = Styled("danger")
    lazy val info    = Styled("info")
  }

  object TableRow {
    def data(data: Modifier*): TableRow = {
      new TableRow(data)
    }
  }
}
