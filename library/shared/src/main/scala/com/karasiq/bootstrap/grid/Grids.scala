package com.karasiq.bootstrap.grid

import scala.language.{implicitConversions, postfixOps}

import com.karasiq.bootstrap.context.RenderingContext

trait Grids { self: RenderingContext ⇒
  import scalaTags.all._

  type GridSystem <: AbstractGridSystem
  val GridSystem: GridSystem

  /** @see
    *   [[https://getbootstrap.com/css/#grid-options]]
    */
  trait AbstractGridSystem {
    type ContainerT = Tag
    type RowT       = Tag

    def container: ContainerT
    def containerFluid: ContainerT
    def row: RowT

    def col: AbstractColumnFactory
    def hidden: AbstractGridVisibilityFactory
    def visible: AbstractGridVisibilityFactory

    def mkRow(md: Modifier*): Tag = {
      row(col(col.maxSize).asDiv(md))
    }

    def visibility(xs: Boolean = true, sm: Boolean = true, md: Boolean = true, lg: Boolean = true): Modifier = {
      Array[Modifier](
        if (xs) visible.xs else hidden.xs,
        if (sm) visible.sm else hidden.sm,
        if (md) visible.md else hidden.md,
        if (lg) visible.lg else hidden.lg
      )
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

    def xs(size: Int): AbstractColumn
    def sm(size: Int): AbstractColumn
    def md(size: Int): AbstractColumn
    def lg(size: Int): AbstractColumn

    def responsive(xsSize: Int, smSize: Int, mdSize: Int, lgSize: Int): AbstractColumn = new AbstractColumn {
      override val createModifier: ModifierT = Seq[ModifierT](xs(xsSize), sm(smSize), md(mdSize), lg(lgSize))
      def size: Int                          = lgSize
      def asDiv: Tag                         = div(createModifier)
    }

    def apply(size: Int): AbstractColumn = this.responsive(size, size, size, size)
  }

  trait AbstractGridVisibility extends ModifierFactory {
    def screenSize: String
    def hidden: Boolean
  }

  trait AbstractGridVisibilityFactory {
    def xs: AbstractGridVisibility
    def sm: AbstractGridVisibility
    def md: AbstractGridVisibility
    def lg: AbstractGridVisibility
    def print: AbstractGridVisibility
  }
}
