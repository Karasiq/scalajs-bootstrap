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
    def xs(size: Int): GridColSize = colModifier("xs", size)
    def sm(size: Int): GridColSize = colModifier("sm", size)
    def md(size: Int): GridColSize = colModifier("md", size)
    def lg(size: Int): GridColSize = colModifier("lg", size)

    def responsive(xsSize: Int, smSize: Int, mdSize: Int, lgSize: Int): GridColSize = new GridColSize {
      override def classMod: Modifier = Seq(xs(xsSize), sm(smSize), md(mdSize), lg(lgSize))
    }

    def apply(size: Int): GridColSize = this.responsive(size, size, size, size)
  }
}
