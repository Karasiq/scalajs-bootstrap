package com.karasiq.bootstrap.test.frontend

import com.karasiq.bootstrap.Bootstrap.default._
import rx._

import scala.language.postfixOps
import scalaTags.all._

sealed abstract class ItemPriority(val style: TableRowStyle)

object ItemPriority {
  case object Low extends ItemPriority(TableRowStyle.success)
  case object Normal extends ItemPriority(TableRowStyle.info)
  case object High extends ItemPriority(TableRowStyle.danger)
  def fromString(s: String): ItemPriority = Seq(Low, Normal, High).find(_.toString == s).get
}

case class TodoListItem(title: String, priority: ItemPriority = ItemPriority.Normal, completed: Boolean = false)

final class TodoList extends BootstrapHtmlComponent {
  val items: Var[Seq[Var[TodoListItem]]] = Var(Nil)

  private def todoItemDialog(title: String, priority: ItemPriority)(f: (String, ItemPriority) ⇒ Unit): Unit = {
    val titleText = Var(title)
    val prioritySelect = FormInput.simpleSelect("Priority", "Low", "Normal", "High")
    prioritySelect.selected.update(Seq(priority.toString))
    Modal("Add/edit item")
      .withBody(Form(
        FormInputGroup(FormInputGroup.label("Title"), FormInputGroup.addon("file-text-o".fontAwesome(FontAwesome.fixedWidth)), FormInputGroup.text(placeholder := "Write description", titleText.reactiveInput)),
        prioritySelect
      ))
      .withButtons(Modal.closeButton("Cancel"), Modal.button("Apply", Modal.dismiss, onclick := Callback.onClick { _ ⇒
        f(titleText.now, ItemPriority.fromString(prioritySelect.selected.now.head))
      }))
      .show(backdrop = false)
  }

  private def editItemDialog(i: Var[TodoListItem]): Unit = {
    todoItemDialog(i.now.title, i.now.priority) { (title, priority) ⇒
      i.update(i.now.copy(title, priority))
    }
  }

  private def addItemDialog(): Unit = {
    todoItemDialog("", ItemPriority.Normal) { (title, priority) ⇒
      items.update(items.now :+ Var(TodoListItem(title, priority)))
    }
  }

  private def removeCompleted(): Unit = {
    items.update(items.now.filterNot(_.now.completed))
  }

  private def addTestData(): Unit = {
    items.update(items.now ++ (for (i <- 1 to 20) yield Var(TodoListItem(s"Test $i", ItemPriority.Low))))
  }

  private def renderItem(i: Var[TodoListItem]): TableRow = {
    def todoTitle = Rx(if (i().completed) s(i().title, color.gray) else b(i().title))
    def buttons = ButtonGroup(ButtonGroupSize.small,
      Button(ButtonStyle.primary)("Edit", onclick := Callback.onClick(_ ⇒ editItemDialog(i))),
      Button(ButtonStyle.danger)("Remove", onclick := Callback.onClick(_ ⇒ items.update(items.now.filter(_.ne(i)))))
    )
    TableRow(
      Seq(
        Seq[Modifier](todoTitle, GridSystem.col(10), onclick := Callback.onClick(_ ⇒ i.update(i.now.copy(completed = !i.now.completed)))),
        Seq[Modifier](buttons, GridSystem.col(2), textAlign.center)
      ),
      Rx(`class` := {
        if (i().completed) "" else i().priority.style.styleClass.getOrElse("")
      }).auto
    )
  }

  override def renderTag(md: Modifier*): Tag = {
    val heading = Rx(Seq[Modifier](
      Seq[Modifier]("Description", GridSystem.col(10)),
      Seq[Modifier]("Actions", GridSystem.col(2)))
    )
    val table = PagedTable(heading, items.map(_.map(renderItem)), 5)

    Panel(style = PanelStyle.success)
      .withHeader(Panel.title("th-list".glyphicon, span("Scala.js Todo", raw("&nbsp;"), Rx(Bootstrap.badge(items().count(i ⇒ !i().completed)))), Panel.buttons(
        Panel.button("plus".glyphicon, onclick := Callback.onClick(_ ⇒ addItemDialog())),
        Panel.button("trash".glyphicon, onclick := Callback.onClick(_ ⇒ removeCompleted())),
        Panel.button("flash".glyphicon, onclick := Callback.onClick(_ ⇒ addTestData()))
      )))
      .renderTag(table.renderTag(TableStyle.bordered, TableStyle.hover, TableStyle.striped, TableStyle.condensed))
  }
}
