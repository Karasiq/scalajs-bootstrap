package com.karasiq.bootstrap.grid

import scala.language.implicitConversions

import com.karasiq.bootstrap.context.{ClassModifiers, RenderingContext}

trait UniversalGrids { self: RenderingContext with Grids with ClassModifiers ⇒
  import scalaTags.all._

  type GridSystem = UniversalGridSystem
  object GridSystem extends UniversalGridSystem

  trait UniversalGridSystem extends AbstractGridSystem {
    val container: Tag      = div(`class` := "container")
    val containerFluid: Tag = div(`class` := "container-fluid")
    val row: Tag            = div(`class` := "row")

    object col extends AbstractColumnFactory {
      val minSize: Int = 1
      val maxSize: Int = 12

      sealed trait GridColSize extends AbstractColumn {
        final def asDiv: Tag = div(this.createModifier)
      }

      @inline
      private[this] def singleColSize(screenSize: String, _size: Int): GridColSize = new GridColSize {
        // require(modifier.nonEmpty && size <= 12 && size > 0, "Invalid grid column properties")
        override val createModifier = ("col-" + screenSize + "-" + _size).addClass
        override val size: Int      = _size
      }

      def xs(size: Int): GridColSize = singleColSize("xs", size)
      def sm(size: Int): GridColSize = singleColSize("sm", size)
      def md(size: Int): GridColSize = singleColSize("md", size)
      def lg(size: Int): GridColSize = singleColSize("lg", size)
    }

    /** @see
      *   [[http://getbootstrap.com/css/#responsive-utilities]]
      */
    object hidden extends AbstractGridVisibilityFactory {
      final class UniversalGridHiddenModifier private[grid] (val screenSize: String) extends AbstractGridVisibility {
        val hidden         = true
        val createModifier = ("hidden-" + screenSize).addClass
      }

      @inline
      private[this] implicit def gridHidden(size: String): UniversalGridHiddenModifier = {
        new UniversalGridHiddenModifier(size)
      }

      lazy val xs: UniversalGridHiddenModifier    = "xs"
      lazy val sm: UniversalGridHiddenModifier    = "sm"
      lazy val md: UniversalGridHiddenModifier    = "md"
      lazy val lg: UniversalGridHiddenModifier    = "lg"
      lazy val print: UniversalGridHiddenModifier = "print"
    }

    /** @see
      *   [[http://getbootstrap.com/css/#responsive-utilities]]
      */
    object visible extends AbstractGridVisibilityFactory {
      class UniversalGridVisibleModifier private[grid] (val screenSize: String, as: String)
          extends AbstractGridVisibility {
        val hidden         = false
        val createModifier = ("visible-" + screenSize + "-" + as).addClass
      }

      final class UniversalGridVisibility private[grid] (screenSize: String)
          extends UniversalGridVisibleModifier(screenSize, "block") {
        val block: UniversalGridVisibleModifier = this
        lazy val inline                         = new UniversalGridVisibleModifier(screenSize, "inline")
        lazy val `inline-block`                 = new UniversalGridVisibleModifier(screenSize, "inline-block")
      }

      @inline
      private[this] implicit def gridVisibility(size: String): UniversalGridVisibility = new UniversalGridVisibility(
        size
      )

      lazy val xs: UniversalGridVisibility    = "xs"
      lazy val sm: UniversalGridVisibility    = "sm"
      lazy val md: UniversalGridVisibility    = "md"
      lazy val lg: UniversalGridVisibility    = "lg"
      lazy val print: UniversalGridVisibility = "print"
    }
  }
}
