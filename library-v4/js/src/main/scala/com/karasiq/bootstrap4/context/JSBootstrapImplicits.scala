package com.karasiq.bootstrap4.context

import scala.language.implicitConversions

import org.scalajs.dom.DOMList
import org.scalajs.jquery.JQuery

trait JSBootstrapImplicits {
  implicit def bootstrapJquery(jq: JQuery): BootstrapJQuery = {
    jq.asInstanceOf[BootstrapJQuery]
  }

  implicit class DOMListIndexedSeq[T](dl: DOMList[T]) extends IndexedSeq[T] {
    override def length: Int = dl.length
    override def apply(idx: Int): T = dl.apply(idx)
  }
}
