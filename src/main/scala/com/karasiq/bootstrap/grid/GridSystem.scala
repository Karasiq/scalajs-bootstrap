package com.karasiq.bootstrap.grid

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.ClassModifier
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
    sealed trait GridColSize extends ClassModifier {
      final def asDiv: ConcreteHtmlTag[dom.html.Div] = div(this.classMod)
    }

    private def colModifier(modifier: String, size: Int): GridColSize = new GridColSize {
      require(modifier.nonEmpty && size <= 12 && size > 0, "Invalid grid column properties")
      override def classMod: Modifier = s"col-$modifier-$size".addClass
    }
    def xs(size: Int) = colModifier("xs", size)
    def sm(size: Int) = colModifier("sm", size)
    def md(size: Int) = colModifier("md", size)
    def lg(size: Int) = colModifier("lg", size)

    def responsive(xsSize: Int, smSize: Int, mdSize: Int, lgSize: Int): GridColSize = new GridColSize {
      override def classMod: Modifier = Seq(xs(xsSize), sm(smSize), md(mdSize), lg(lgSize))
    }

    def apply(size: Int): GridColSize = this.responsive(size, size, size, size)
  }

  object hidden {
    sealed trait GridHiddenModifier extends ClassModifier
    private implicit def gridHidden(size: String): GridHiddenModifier = new GridHiddenModifier {
      def classMod = s"hidden-$size".addClass
    }

    val xs: GridHiddenModifier = "xs"
    val sm: GridHiddenModifier = "sm"
    val md: GridHiddenModifier = "md"
    val lg: GridHiddenModifier = "lg"
  }

  object visible {
    sealed trait GridVisibleModifier extends ClassModifier
    private def gridVisibleOn(size: String, as: String): GridVisibleModifier = new GridVisibleModifier {
      def classMod = s"visible-$size-$as".addClass
    }

    sealed class GridVisibility(size: String) {
      def block = gridVisibleOn(size, "block")
      def inline = gridVisibleOn(size, "inline")
      def `inline-block` = gridVisibleOn(size, "inline-block")
      def apply() = block // Default
    }

    val xs = new GridVisibility("xs")
    val sm = new GridVisibility("sm")
    val md = new GridVisibility("md")
    val lg = new GridVisibility("lg")
  }
}
