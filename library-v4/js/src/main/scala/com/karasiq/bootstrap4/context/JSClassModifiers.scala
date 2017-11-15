package com.karasiq.bootstrap4.context

import scala.language.postfixOps

import com.karasiq.bootstrap4.utils.ClassModifiers

trait JSClassModifiers extends ClassModifiers { self: JSRenderingContext ⇒
  def addClass(element: Element, className: String): Unit = {
    element.classList.add(className)
  }

  def removeClass(element: Element, className: String): Unit = {
    element.classList.remove(className)
  }
}
