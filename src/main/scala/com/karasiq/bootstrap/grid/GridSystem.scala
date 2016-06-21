package com.karasiq.bootstrap.grid

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ModifierFactory
import org.scalajs.dom

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
      override def createModifier: Modifier = s"col-$modifier-$size".addClass
    }
    def xs(size: Int) = singleColSize("xs", size)
    def sm(size: Int) = singleColSize("sm", size)
    def md(size: Int) = singleColSize("md", size)
    def lg(size: Int) = singleColSize("lg", size)

    def responsive(xsSize: Int, smSize: Int, mdSize: Int, lgSize: Int): GridColSize = new GridColSize {
      override def createModifier: Modifier = Seq(xs(xsSize), sm(smSize), md(mdSize), lg(lgSize))
    }

    def apply(size: Int): GridColSize = this.responsive(size, size, size, size)
  }

  object hidden {
    final class GridHiddenModifier private[grid] (size: String) extends ModifierFactory {
      def createModifier = s"hidden-$size".addClass
    }

    private implicit def gridHidden(size: String): GridHiddenModifier = new GridHiddenModifier(size)

    val xs: GridHiddenModifier = "xs"
    val sm: GridHiddenModifier = "sm"
    val md: GridHiddenModifier = "md"
    val lg: GridHiddenModifier = "lg"
  }

  object visible {
    final class GridVisibleModifier private[grid] (size: String, as: String) extends ModifierFactory {
      def createModifier = s"visible-$size-$as".addClass
    }

    final class GridVisibility private[grid](size: String) extends ModifierFactory {
      def block = new GridVisibleModifier(size, "block")
      def inline = new GridVisibleModifier(size, "inline")
      def `inline-block` = new GridVisibleModifier(size, "inline-block")
      def createModifier = block.createModifier // Default
    }

    val xs = new GridVisibility("xs")
    val sm = new GridVisibility("sm")
    val md = new GridVisibility("md")
    val lg = new GridVisibility("lg")
  }
}
