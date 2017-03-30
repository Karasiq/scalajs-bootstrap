package com.karasiq.bootstrap.utils

import org.scalajs.dom.DOMList
import org.scalajs.jquery.JQuery

import scala.language.implicitConversions

trait JSBootstrapImplicits {
  implicit def bootstrapJquery(jq: JQuery): BootstrapJQuery = {
    jq.asInstanceOf[BootstrapJQuery]
  }

  implicit class DOMListIndexedSeq[T](dl: DOMList[T]) extends IndexedSeq[T] {
    override def length: Int = dl.length
    override def apply(idx: Int): T = dl.apply(idx)
  }
}
