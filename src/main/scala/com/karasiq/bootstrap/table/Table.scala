package com.karasiq.bootstrap.table

import org.scalajs.dom

import scalatags.JsDom.all._

class Table {
  private val head: dom.Element = thead().render

  private val body: dom.Element = tbody().render

  def setHeading(heading: Seq[String]): Unit = {
    head.innerHTML = ""
    head.appendChild(tr(for (h <- heading) yield th(h)).render)
  }

  def setContent(content: Seq[TableRow]): Unit = {
    body.innerHTML = ""
    for (TableRow(data, modifiers @ _*) <- content) {
      body.appendChild(tr(
        modifiers,
        for (col <- data) yield td(col)
      ).render)
    }
  }

  def render(style: String*): Tag = {
    val cls = (Seq("table") ++ style).mkString(" ")
    table(`class` := cls)(
      head,
      body
    )
  }
}
