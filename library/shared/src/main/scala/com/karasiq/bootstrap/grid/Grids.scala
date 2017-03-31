package com.karasiq.bootstrap.grid

import com.karasiq.bootstrap.context.BootstrapBundle

import scala.language.{implicitConversions, postfixOps}

trait Grids { self: BootstrapBundle ⇒
  import scalaTags.all._

  /**
    * @see [[https://getbootstrap.com/css/#grid-options]]
    */
  object GridSystem {
    lazy val container: Tag = div(`class` := "container")
    lazy val containerFluid: Tag = div(`class` := "container-fluid")
    lazy val row: Tag = div(`class` := "row")

    def mkRow(md: Modifier*): Tag = {
      row(col(col.maxSize).asDiv(md))
    }

    object col {
      val minSize: Int = 1
      val maxSize: Int = 12

      sealed trait GridColSize extends ModifierFactory {
        final def asDiv: Tag = div(this.createModifier)
      }

      @inline
      private[this] def singleColSize(modifier: String, size: Int): GridColSize = new GridColSize {
        // require(modifier.nonEmpty && size <= 12 && size > 0, "Invalid grid column properties")
        override val createModifier: ModifierT = ("col-" + modifier + "-" + size).addClass
      }
      
      def xs(size: Int): GridColSize = singleColSize("xs", size)
      def sm(size: Int): GridColSize = singleColSize("sm", size)
      def md(size: Int): GridColSize = singleColSize("md", size)
      def lg(size: Int): GridColSize = singleColSize("lg", size)

      def responsive(xsSize: Int, smSize: Int, mdSize: Int, lgSize: Int): GridColSize = new GridColSize {
        override val createModifier: ModifierT = Seq[ModifierT](xs(xsSize), sm(smSize), md(mdSize), lg(lgSize))
      }

      def apply(size: Int): GridColSize = this.responsive(size, size, size, size)
    }

    /**
      * @see [[http://getbootstrap.com/css/#responsive-utilities]]
      */
    object hidden {
      final class GridHiddenModifier private[grid](size: String) extends ModifierFactory {
        val createModifier: ModifierT = ("hidden-" + size).addClass
      }

      @inline
      private[this] implicit def gridHidden(size: String): GridHiddenModifier = new GridHiddenModifier(size)

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
      final class GridVisibleModifier private[grid](size: String, as: String) extends ModifierFactory {
        val createModifier: ModifierT = ("visible-" + size + "-" + as).addClass
      }

      final class GridVisibility private[grid](size: String) extends ModifierFactory {
        lazy val block = new GridVisibleModifier(size, "block")
        lazy val inline = new GridVisibleModifier(size, "inline")
        lazy val `inline-block` = new GridVisibleModifier(size, "inline-block")
        lazy val createModifier: Modifier = block.createModifier // Default
      }

      @inline
      private[this] implicit def gridVisibility(size: String): GridVisibility = new GridVisibility(size)

      lazy val xs: GridVisibility = "xs"
      lazy val sm: GridVisibility = "sm"
      lazy val md: GridVisibility = "md"
      lazy val lg: GridVisibility = "lg"
      lazy val print: GridVisibility = "print"
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
}

