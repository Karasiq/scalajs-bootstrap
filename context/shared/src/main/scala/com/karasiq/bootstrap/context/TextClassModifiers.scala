package com.karasiq.bootstrap.context

import scala.language.postfixOps
import scalatags.Escaping
import scalatags.text.Builder.ValueSource

trait TextClassModifiers extends ClassModifiers { self: TextRenderingContext ⇒
  def addClass(element: Element, className: String): Unit = {
    modifyClasses(element, _ + className)
  }

  def removeClass(element: Element, className: String): Unit = {
    modifyClasses(element, _ - className)
  }

  private[this] case class ClassesValueSource(classes: Set[String]) extends ValueSource {
    def appendAttrValue(writer: java.io.Writer): Unit = {
      if (classes.nonEmpty) {
        val iterator = classes.iterator
        Escaping.escape(iterator.next(), writer)
        while (iterator.hasNext) {
          writer.write(" ")
          Escaping.escape(iterator.next(), writer)
        }
      }
    }
  }

  private[this] def modifyClasses(e: Element, f: Set[String] ⇒ Set[String]): Unit = {
    import scalatags.text.Builder._
    def extractClasses(attr: ValueSource): Set[String] = attr match {
      case ClassesValueSource(classes) ⇒
        classes

      case GenericAttrValueSource(classStr) ⇒
        classStr.split("\\s+").filter(_.nonEmpty).toSet

      case ChainedAttributeValueSource(head, tail) ⇒
        Iterator(head, tail).flatMap(extractClasses).toSet

      case _ ⇒
        Set.empty
    }

    e.attrIndex("class") match {
      case -1 ⇒
        val newValue = f(Set.empty)
        if (newValue.nonEmpty) e.setAttr("class", ClassesValueSource(newValue))

      case classIndex ⇒
        val (key, value) = e.attrs(classIndex)
        val newValue     = f(extractClasses(value))
        e.attrs(classIndex) = (key, ClassesValueSource(newValue))
    }
  }
}
