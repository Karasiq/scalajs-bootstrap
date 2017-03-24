package com.karasiq.bootstrap_text

import com.karasiq.bootstrap_text.icons._

import scala.language.implicitConversions
import scalatags.Escaping
import scalatags.Text.all._
import scalatags.text.Builder.ValueSource

//noinspection MutatorLikeMethodIsParameterless,ConvertExpressionToSAM
object BootstrapImplicits {
  type Element = scalatags.text.Builder

  implicit def bootstrapHtmlComponentToTag(bc: BootstrapHtmlComponent): bc.RenderedTag = {
    bc.renderTag()
  }

  implicit def renderBootstrapComponent(bc: BootstrapComponent): Modifier = {
    bc.render()
  }

  implicit class HtmlClassOptOps(private val className: Option[String]) extends AnyVal {
    def classOpt: Modifier = new Modifier {
      override def applyTo(t: Element): Unit = {
        className.foreach(_.addClass.applyTo(t))
      }
    }
  }

  implicit class BootstrapIconsOps(private val iconName: String) extends AnyVal {
    def glyphicon: BootstrapGlyphicon = BootstrapGlyphicon(iconName)
    def fontAwesome(styles: FontAwesomeStyle*): FontAwesomeIcon = FontAwesome(iconName, styles:_*)
  }

  implicit def stringToBootstrapIcons(str: String): IconModifier = {
    str.glyphicon
  }

  private[this] final case class ClassesValueSource(classes: Set[String]) extends ValueSource {
    def appendAttrValue(strb: StringBuilder): Unit = {
      if (classes.nonEmpty) {
        val iterator = classes.iterator
        Escaping.escape(iterator.next(), strb)
        while (iterator.hasNext) {
          strb += ' '
          Escaping.escape(iterator.next(), strb)
        }
      }
    }
  }

  implicit class HtmlClassOps(private val className: String) extends AnyVal {
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
          val newValue = f(extractClasses(value))
          e.attrs(classIndex) = (key, ClassesValueSource(newValue))
      }
    }

    def addClass: Modifier = new Modifier {
      override def applyTo(t: Element): Unit = {
        modifyClasses(t, _ + className)
      }
    }

    def removeClass: Modifier = new Modifier {
      override def applyTo(t: Element): Unit = {
        modifyClasses(t, _ - className)
      }
    }

    def classIf(state: Boolean): Modifier = new Modifier {
      override def applyTo(t: Element): Unit = {
        if (state) {
          addClass.applyTo(t)
        } else {
          removeClass.applyTo(t)
        }
      }
    }
  }
}
