package com.karasiq.bootstrap.test.frontend

import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.buttons._
import com.karasiq.bootstrap.form.{Form, FormInput}
import com.karasiq.bootstrap.grid.GridSystem
import com.karasiq.bootstrap.modal.Modal
import com.karasiq.bootstrap.panel.{Panel, PanelStyle}
import com.karasiq.bootstrap.table.{PagedTable, TableRow, TableRowStyle, TableStyle}
import com.karasiq.bootstrap.{Bootstrap, BootstrapHtmlComponent}
import org.scalajs.dom
import rx._

import scala.language.postfixOps
import scalatags.JsDom.all._

sealed trait ItemPriority {
  def style: TableRowStyle
}

object ItemPriority {
  case object Low extends ItemPriority {
    override def style: TableRowStyle = TableRow.success
  }
  case object Normal extends ItemPriority {
    override def style: TableRowStyle = TableRow.default
  }
  case object High extends ItemPriority {
    override def style: TableRowStyle = TableRow.danger
  }

  def fromString(s: String): ItemPriority = s match {
    case "Low" ⇒
      Low

    case "Normal" ⇒
      Normal

    case "High" ⇒
      High
  }
}

sealed trait TodoListItem {
  val title: Var[String]
  val priority: Var[ItemPriority]
  val completed: Var[Boolean]
}

object TodoListItem {
  def apply(t: String, p: ItemPriority = ItemPriority.Normal, c: Boolean = false): TodoListItem = {
    new TodoListItem {
      override val title: Var[String] = Var(t)
      override val priority: Var[ItemPriority] = Var(p)
      override val completed: Var[Boolean] = Var(c)
    }
  }
}

final class TodoList(implicit ctx: Ctx.Owner) extends BootstrapHtmlComponent[dom.html.Div] {
  val items: Var[Seq[TodoListItem]] = Var(Nil)

  private def todoItemDialog(title: String, priority: ItemPriority)(f: (String, ItemPriority) ⇒ Unit): Unit = {
    val titleText = Var(title)
    val prioritySelect = FormInput.select("Priority", "Low", "Normal", "High")
    prioritySelect.selected.update(Seq(priority.toString))
    Modal("Add/edit item")
      .withBody(Form(FormInput.text("Title", placeholder := "Write description", titleText.reactiveInput), prioritySelect))
      .withButtons(Modal.closeButton("Cancel"), Modal.button("Apply", Modal.dismiss, onclick := Bootstrap.jsClick { _ ⇒
        f(titleText.now, ItemPriority.fromString(prioritySelect.selected.now.head))
      }))
      .show(backdrop = false)
  }

  private def editItemDialog(i: TodoListItem): Unit = {
    todoItemDialog(i.title.now, i.priority.now) { (title, priority) ⇒
      i.title.update(title)
      i.priority.update(priority)
    }
  }

  private def addItemDialog(): Unit = {
    todoItemDialog("", ItemPriority.Normal) { (title, priority) ⇒
      items.update(items.now :+ TodoListItem(title, priority))
    }
  }

  private def removeCompleted(): Unit = {
    items.update(items.now.filterNot(_.completed.now))
  }

  private def addTestData(): Unit = {
    items.update(items.now ++ (for (i <- 1 to 20) yield TodoListItem(s"Test $i", ItemPriority.Low)))
  }

  private def renderItem(i: TodoListItem): TableRow = {
    def todoTitle = Rx(if (i.completed()) s(i.title()) else b(i.title()))
    def buttons = ButtonGroup(ButtonGroupSize.small,
      Button(ButtonStyle.primary).renderTag("Edit", onclick := Bootstrap.jsClick(_ ⇒ editItemDialog(i))),
      Button(ButtonStyle.danger).renderTag("Remove", onclick := Bootstrap.jsClick(_ ⇒ items.update(items.now.filter(_.ne(i)))))
    )
    TableRow(
      Seq(
        Seq[Modifier](todoTitle, GridSystem.col(9), onclick := Bootstrap.jsClick(_ ⇒ i.completed.update(!i.completed.now))),
        Seq[Modifier](buttons, GridSystem.col(2), textAlign.center)
      ),
      Rx[AutoModifier](`class` := {
        if (i.completed()) "" else i.priority().style.styleClass.getOrElse("")
      })
    )
  }

  override def renderTag(md: Modifier*): RenderedTag = {
    val table = PagedTable(Rx(Seq[Modifier]("Description", "Actions")), items.map(_.map(renderItem)), 5)
    Panel(style = PanelStyle.success)
      .withHeader(Panel.title("th-list", span("Scala.js Bootstrap ToDo list", raw("&nbsp;"), Rx(Bootstrap.badge(items().count(i ⇒ !i.completed())))), Panel.buttons(
        Panel.button("plus", onclick := Bootstrap.jsClick(_ ⇒ addItemDialog())),
        Panel.button("trash", onclick := Bootstrap.jsClick(_ ⇒ removeCompleted())),
        Panel.button("flash", onclick := Bootstrap.jsClick(_ ⇒ addTestData()))
      )))
      .renderTag(table.renderTag(TableStyle.bordered, TableStyle.hover, TableStyle.striped, TableStyle.condensed))
  }
}
