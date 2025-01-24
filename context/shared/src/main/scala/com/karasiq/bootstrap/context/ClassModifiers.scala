package com.karasiq.bootstrap.context

import rx.Rx

import com.karasiq.bootstrap.context.ReactiveBinds.Modify

trait ClassModifiers { self: RenderingContext ⇒
  import scalaTags.all._

  def addClass(element: Element, className: String): Unit
  def removeClass(element: Element, className: String): Unit

  sealed trait ClassModifier extends Modifier {
    def className: String
    def classAdded: Boolean
    def classRemoved: Boolean
  }

  case class ClassAdd(className: String) extends ClassModifier {
    val classAdded                = true
    val classRemoved              = false
    def applyTo(t: Element): Unit = addClass(t, className)
  }

  case class ClassRemove(className: String) extends ClassModifier {
    val classAdded                = false
    val classRemoved              = true
    def applyTo(t: Element): Unit = removeClass(t, className)
  }

  // noinspection MutatorLikeMethodIsParameterless
  implicit class HtmlClassOps(className: String) {
    def addClass: ClassAdd = {
      ClassAdd(className)
    }

    def removeClass: ClassRemove = {
      ClassRemove(className)
    }

    def classIf(state: Boolean): ClassModifier = {
      if (state) addClass else removeClass
    }

    def classIf(state: Rx[Boolean]): Modifier = {
      writeModifier(
        Modify[Element, Boolean](
          state,
          (e, v) ⇒ {
            if (v) {
              ClassModifiers.this.addClass(e, className)
            } else {
              ClassModifiers.this.removeClass(e, className)
            }
          }
        )
      )
    }
  }

  implicit class HtmlClassOptOps(className: Option[String]) {
    def classOpt: Modifier = new Modifier {
      override def applyTo(t: Element): Unit = {
        className.foreach(addClass(t, _))
      }
    }
  }
}
