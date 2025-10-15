package com.karasiq.bootstrap.context

import org.scalajs.dom.DOMList

trait JSImplicits {
  implicit class DOMListIndexedSeq[T](dl: DOMList[T]) extends IndexedSeq[T] {
    override def length: Int        = dl.length
    override def apply(idx: Int): T = dl.apply(idx)
  }
}
