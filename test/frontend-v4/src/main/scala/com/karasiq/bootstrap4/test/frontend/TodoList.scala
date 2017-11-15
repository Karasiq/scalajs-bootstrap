package com.karasiq.bootstrap4.test.frontend

import java.util.UUID

import scala.language.postfixOps

import rx._

import com.karasiq.bootstrap4.Bootstrap.default._
import scalaTags.all._

object TodoList {
  sealed abstract class ItemPriority(val style: TableRowStyle)

  object ItemPriority {
    case object Low extends ItemPriority(TableRowStyle.success)
    case object Normal extends ItemPriority(TableRowStyle.info)
    case object High extends ItemPriority(TableRowStyle.danger)
    def fromString(s: String): ItemPriority = Seq(Low, Normal, High).find(_.toString == s).get
  }

  case class Item(title: String, priority: ItemPriority = ItemPriority.Normal, completed: Boolean = false)

  def apply(): TodoList = {
    new TodoList()
  }
}

final class TodoList extends BootstrapHtmlComponent {
  import TodoList._

  val items: Var[Seq[Var[Item]]] = Var(Nil)

  def removeCompleted(): Unit = {
    items() = items.now.filterNot(_.now.completed)
  }

  def addTestData(): Unit = {
    items() = items.now ++ (for (_ <- 1 to 20) yield Var(Item(s"Test ${UUID.randomUUID()}", ItemPriority.Low)))
  }

  private[this] def showDialog(title: String, priority: ItemPriority)(onApply: (String, ItemPriority) ⇒ Unit): Unit = {
    val titleText = Var(title)
    val prioritySelect = FormInput.simpleSelect("Priority", "Low", "Normal", "High")
    prioritySelect.selected.update(Seq(priority.toString))
    Modal("Add/edit item")
      .withBody(Form(
        FormInputGroup(FormInputGroup.label("Title"), FormInputGroup.addon("file-text-o".fontAwesome(FontAwesome.fixedWidth)), FormInputGroup.text(placeholder := "Write description", titleText.reactiveInput)),
        prioritySelect
      ))
      .withButtons(Modal.closeButton("Cancel"), Modal.button("Apply", Modal.dismiss, onclick := Callback.onClick { _ ⇒
        onApply(titleText.now, ItemPriority.fromString(prioritySelect.selected.now.head))
      }))
      .show(backdrop = false)
  }

  def showEditDialog(item: Var[Item]): Unit = {
    showDialog(item.now.title, item.now.priority) { (title, priority) ⇒
      item() = item.now.copy(title, priority)
    }
  }

  def showAddDialog(): Unit = {
    showDialog("", ItemPriority.Normal) { (title, priority) ⇒
      items() = items.now :+ Var(Item(title, priority))
    }
  }

  private[this] def renderItem(item: Var[Item]): TableRow = {
    def todoTitle = Rx(if (item().completed) s(item().title, color.gray) else b(item().title))
    def buttons = ButtonGroup(ButtonGroupSize.small,
      Button(ButtonStyle.primary)("Edit", onclick := Callback.onClick(_ ⇒ showEditDialog(item))),
      Button(ButtonStyle.danger)("Remove", onclick := Callback.onClick(_ ⇒ items.update(items.now.filter(_.ne(item)))))
    )
    TableRow(
      Seq(
        Seq[Modifier](todoTitle, GridSystem.col(10), onclick := Callback.onClick(_ ⇒ item.update(item.now.copy(completed = !item.now.completed)))),
        Seq[Modifier](buttons, GridSystem.col(2), textAlign.center)
      ),
      Rx(`class` := {
        if (item().completed) "" else item().priority.style match {
          case TableRowStyle.Default ⇒ ""
          case style: TableRowStyle.Styled ⇒ style.className
        }
      }).auto
    )
  }

  override def renderTag(md: ModifierT*): TagT = {
    val heading = Rx(Seq[Modifier](
      Seq[Modifier]("Description", GridSystem.col(10)),
      Seq[Modifier]("Actions", GridSystem.col(2)))
    )
    val table = PagedTable(heading, items.map(_.map(renderItem)), 5)

    Card()
      .withHeader("th-list".faFwIcon, Bootstrap.nbsp, span("Scala.js Todo", Rx(Bootstrap.badge(items().count(i ⇒ !i().completed)))), Card.buttons(
        Card.button("plus".faFwIcon, onclick := Callback.onClick(_ ⇒ showAddDialog())),
        Card.button("trash".faFwIcon, onclick := Callback.onClick(_ ⇒ removeCompleted())),
        Card.button("flash".faFwIcon, onclick := Callback.onClick(_ ⇒ addTestData()))
      ))
      .withBody(table.renderTag(TableStyle.bordered, TableStyle.hover, TableStyle.striped, TableStyle.small))
  }
}
