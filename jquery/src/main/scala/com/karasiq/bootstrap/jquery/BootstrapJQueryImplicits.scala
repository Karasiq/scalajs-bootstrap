package com.karasiq.bootstrap.jquery

import scala.language.implicitConversions

import org.scalajs.jquery.JQuery

trait BootstrapJQueryImplicits {
  implicit def implicitBootstrapJQuery(jq: JQuery): BootstrapJQuery = {
    jq.asInstanceOf[BootstrapJQuery]
  }
}

object BootstrapJQueryImplicits extends BootstrapJQueryImplicits