package com.karasiq.bootstrap4.card

import com.karasiq.bootstrap4.context.RenderingContext
import com.karasiq.bootstrap4.utils.Utils

trait CardStyles { self: RenderingContext with Utils ⇒
  import scalaTags.all._

  sealed trait CardStyle extends StyleModifier

  object CardStyle {
    case object Default extends CardStyle {
      def styleName = "default"
      def createModifier = ()
    }

    final case class Styled(styleName: String) extends CardStyle with StyleClassModifier  {
      val className = s"card-$styleName"
      val createModifier = className.addClass
    }

    def default = Default
    def primary = Styled("primary")
    def success = Styled("success")
    def info = Styled("info")
    def warning = Styled("warning")
    def danger = Styled("danger")
  }
}
