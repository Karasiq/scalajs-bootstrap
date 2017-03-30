package com.karasiq.bootstrap.utils

import com.karasiq.bootstrap.context.ReactiveBinds.Modify
import com.karasiq.bootstrap.context.RenderingContext
import rx.Rx

trait ClassModifiers { self: RenderingContext ⇒
  import scalaTags.all._

  def addClass(element: Element, className: String): Unit
  def removeClass(element: Element, className: String): Unit

  //noinspection MutatorLikeMethodIsParameterless
  implicit class HtmlClassOps(className: String) {
    def addClass: Modifier = new Modifier {
      override def applyTo(t: Element): Unit = {
        ClassModifiers.this.addClass(t, className)
      }
    }

    def removeClass: Modifier = new Modifier {
      override def applyTo(t: Element): Unit = {
        ClassModifiers.this.removeClass(t, className)
      }
    }

    def classIf(state: Boolean): Modifier = new Modifier {
      override def applyTo(t: Element): Unit = {
        if (state) {
          ClassModifiers.this.addClass(t, className)
        } else {
          ClassModifiers.this.removeClass(t, className)
        }
      }
    }

    def classIf(state: Rx[Boolean]): Modifier = {
      writeModifier(Modify[Element, Boolean](state, (e, v) ⇒ {
        if (v) {
          ClassModifiers.this.addClass(e, className)
        } else {
          ClassModifiers.this.removeClass(e, className)
        }
      }))
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
