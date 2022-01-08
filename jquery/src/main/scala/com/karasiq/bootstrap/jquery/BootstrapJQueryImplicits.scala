package com.karasiq.bootstrap.jquery

import scala.language.implicitConversions

import com.karasiq.bootstrap.jquery.JQueryContext.JQuery

trait BootstrapJQueryImplicits {
  implicit def implicitBootstrapJQuery(jq: JQuery): BootstrapJQuery = {
    jq.asInstanceOf[BootstrapJQuery]
  }
}

object BootstrapJQueryImplicits extends BootstrapJQueryImplicits
