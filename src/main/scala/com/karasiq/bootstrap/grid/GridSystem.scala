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

  object pull {
    final class GridColPull(name: String) {
      def left: ClassModifier = new ClassModifier {
        override def classMod: Modifier = s"pull-$name-left".addClass
      }

      def right: ClassModifier = new ClassModifier {
        override def classMod: Modifier = s"pull-$name-right".addClass
      }
    }

    def xs = new GridColPull("xs")
    def sm = new GridColPull("sm")
    def md = new GridColPull("md")
    def lg = new GridColPull("lg")
  }
}
