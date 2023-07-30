package com.karasiq.bootstrap4.grid

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

      private[this] def singleColSize(screenSize: String, _size: Int): GridColSize = new GridColSize {
        val className = {
          val baseName = if (screenSize.nonEmpty) "col-" + screenSize else "col"
          if (_size == 0) baseName else baseName + "-" + _size
        }
        override val createModifier = className.addClass
        override val size: Int      = _size
      }

      def xs(size: Int): GridColSize = singleColSize("", size)
      def sm(size: Int): GridColSize = singleColSize("sm", size)
      def md(size: Int): GridColSize = singleColSize("md", size)
      def lg(size: Int): GridColSize = singleColSize("lg", size)
      def xl(size: Int): GridColSize = singleColSize("xl", size)
    }
  }
}
