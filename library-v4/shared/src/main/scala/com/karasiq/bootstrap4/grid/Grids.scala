package com.karasiq.bootstrap4.grid

import scala.language.{implicitConversions, postfixOps}

import com.karasiq.bootstrap.context.RenderingContext

trait Grids { self: RenderingContext ⇒
  import scalaTags.all._

  type GridSystem <: AbstractGridSystem
  val GridSystem: GridSystem

  /** @see
    *   [[https://getbootstrap.com/docs/4.0/layout/grid/]]
    */
  trait AbstractGridSystem {
    type ContainerT = Tag
    type RowT       = Tag

    def container: ContainerT
    def containerFluid: ContainerT
    def row: RowT

    def col: AbstractColumnFactory

    def mkRow(md: Modifier*): Tag = {
      row(col(col.maxSize).asDiv(md))
    }
  }

  trait AbstractColumn extends ModifierFactory {
    def size: Int
    def asDiv: Tag
    def apply(md: ModifierT*): Tag = this.asDiv.apply(md: _*)
  }

  trait AbstractColumnFactory {
    def minSize: Int
    def maxSize: Int

    def xs(size: Int = 0): AbstractColumn
    def sm(size: Int = 0): AbstractColumn
    def md(size: Int = 0): AbstractColumn
    def lg(size: Int = 0): AbstractColumn
    def xl(size: Int = 0): AbstractColumn

    def responsive(
        xsSize: Int = maxSize,
        smSize: Int = maxSize,
        mdSize: Int = maxSize,
        lgSize: Int = maxSize,
        xlSize: Int = maxSize
    ): AbstractColumn = new AbstractColumn {
      override val createModifier: ModifierT =
        Seq[ModifierT](xs(xsSize), sm(smSize), md(mdSize), lg(lgSize), xl(xlSize))
      def size: Int  = lgSize
      def asDiv: Tag = div(createModifier)
    }

    def apply(size: Int): AbstractColumn = this.responsive(size, size, size, size, size)
  }
}
