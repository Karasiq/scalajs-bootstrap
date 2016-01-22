package com.karasiq.bootstrap.table

import org.scalajs.dom

import scalatags.JsDom.all._

class Table(style: Seq[String] = Nil) {
  protected val head: dom.Element = thead().render

  protected val body: dom.Element = tbody().render

  def setHeading(heading: Seq[String]): Unit = {
    head.innerHTML = ""
    heading.foreach { h ⇒
      head.appendChild(th(h).render)
    }
  }

  def setContent(content: Seq[TableRow]): Unit = {
    body.innerHTML = ""
    for (TableRow(data, styles) <- content) {
      body.appendChild(tr(
        `class` := styles.mkString(" "),
        for (col <- data) yield td(col)
      ).render)
    }
  }

  def render: Tag = {
    val cls = (Seq("table") ++ style).mkString(" ")
    table(`class` := cls)(
      head,
      body
    )
  }
}
