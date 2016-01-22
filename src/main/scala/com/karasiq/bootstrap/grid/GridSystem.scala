package com.karasiq.bootstrap.grid

import scalatags.JsDom.all._

/**
  * @see [[https://getbootstrap.com/css/#grid-options]]
  */
object GridSystem {
  def container: Tag = div(`class` := "container")
  def row: Tag = div(`class` := "row")

  private case class Column(modifier: String, size: Int) {
    require(modifier.nonEmpty && size <= 12 && size > 0, "Invalid grid column properties")

    def render: Tag = div(`class` := s"col-$modifier-$size")
  }

  object col {
    def xs(size: Int): Tag = Column("xs", size).render
    def sm(size: Int): Tag = Column("sm", size).render
    def md(size: Int): Tag = Column("md", size).render
    def lg(size: Int): Tag = Column("lg", size).render
  }
}
