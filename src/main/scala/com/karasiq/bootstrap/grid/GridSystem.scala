package com.karasiq.bootstrap.grid

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ModifierFactory
import org.scalajs.dom
import org.scalajs.dom.Element

import scalatags.JsDom.all._

/**
  * @see [[https://getbootstrap.com/css/#grid-options]]
  */
object GridSystem {
  def container: Tag = div("container".addClass)
  def containerFluid: Tag = div("container-fluid".addClass)
  def row: Tag = div("row".addClass)

  def mkRow(md: Modifier*): Tag = {
    row(col(12).asDiv(md))
  }

  object col {
    sealed trait GridColSize extends ModifierFactory {
      final def asDiv: ConcreteHtmlTag[dom.html.Div] = div(this.createModifier)
    }

    private def singleColSize(modifier: String, size: Int): GridColSize = new GridColSize {
      require(modifier.nonEmpty && size <= 12 && size > 0, "Invalid grid column properties")
      override val createModifier: Modifier = s"col-$modifier-$size".addClass
    }
    def xs(size: Int) = singleColSize("xs", size)
    def sm(size: Int) = singleColSize("sm", size)
    def md(size: Int) = singleColSize("md", size)
    def lg(size: Int) = singleColSize("lg", size)

    def responsive(xsSize: Int, smSize: Int, mdSize: Int, lgSize: Int): GridColSize = new GridColSize {
      override val createModifier: Modifier = Seq(xs(xsSize), sm(smSize), md(mdSize), lg(lgSize))
    }

    def apply(size: Int): GridColSize = this.responsive(size, size, size, size)
  }

  /**
    * @see [[http://getbootstrap.com/css/#responsive-utilities]]
    */
  object hidden {
    final class GridHiddenModifier private[grid] (size: String) extends ModifierFactory {
      val createModifier = s"hidden-$size".addClass
    }

    private implicit def gridHidden(size: String): GridHiddenModifier = new GridHiddenModifier(size)

    lazy val xs: GridHiddenModifier = "xs"
    lazy val sm: GridHiddenModifier = "sm"
    lazy val md: GridHiddenModifier = "md"
    lazy val lg: GridHiddenModifier = "lg"
    lazy val print: GridHiddenModifier = "print"
  }

  /**
    * @see [[http://getbootstrap.com/css/#responsive-utilities]]
    */
  object visible {
    final class GridVisibleModifier private[grid] (size: String, as: String) extends ModifierFactory {
      val createModifier = s"visible-$size-$as".addClass
    }

    final class GridVisibility private[grid](size: String) extends ModifierFactory {
      lazy val block = new GridVisibleModifier(size, "block")
      lazy val inline = new GridVisibleModifier(size, "inline")
      lazy val `inline-block` = new GridVisibleModifier(size, "inline-block")
      def createModifier = block.createModifier // Default
    }

    lazy val xs = new GridVisibility("xs")
    lazy val sm = new GridVisibility("sm")
    lazy val md = new GridVisibility("md")
    lazy val lg = new GridVisibility("lg")
    lazy val print = new GridVisibility("print")
  }

  def visibility(xs: Boolean = true, sm: Boolean = true, md: Boolean = true, lg: Boolean = true): Modifier = new Modifier {
    def applyTo(t: Element) = {
      Array(
        if (xs) visible.xs else hidden.xs,
        if (sm) visible.sm else hidden.sm,
        if (md) visible.md else hidden.md,
        if (lg) visible.lg else hidden.lg
      ).foreach(_.applyTo(t))
    }
  }
}
