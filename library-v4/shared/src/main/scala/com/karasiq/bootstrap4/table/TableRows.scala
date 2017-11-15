package com.karasiq.bootstrap4.table

import com.karasiq.bootstrap4.context.RenderingContext
import com.karasiq.bootstrap4.utils.{ClassModifiers, Utils}

trait TableRows { self: RenderingContext with ClassModifiers with Utils ⇒
  import scalaTags.all._

  trait TableRow {
    def columns: Seq[Modifier]
    def modifiers: Modifier
  }

  sealed trait TableRowStyle extends StyleModifier {
    def styleName: String
  }

  //noinspection TypeAnnotation
  object TableRowStyle {
    case object Default extends TableRowStyle {
      def styleName = "default"
      val createModifier = Bootstrap.noModifier
    }

    final case class Styled(styleName: String) extends TableRowStyle with StyleClassModifier  {
      val className = s"table-$styleName"
      val createModifier = className.addClass
    }

    def default = Default
    lazy val active = Styled("active")
    lazy val primary = Styled("primary")
    lazy val secondary = Styled("secondary")
    lazy val success = Styled("success")
    lazy val warning = Styled("warning")
    lazy val danger = Styled("danger")
    lazy val info = Styled("info")
    lazy val light = Styled("light")
    lazy val dark = Styled("dark")
  }

  object TableRow {
    def apply(data: Seq[Modifier], ms: Modifier*): TableRow = new TableRow {
      override def modifiers: Modifier = ms
      override def columns: Seq[Modifier] = data
    }

    def data(data: Modifier*): TableRow = {
      apply(data)
    }
  }
}
