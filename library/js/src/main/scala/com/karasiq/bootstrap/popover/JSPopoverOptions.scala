package com.karasiq.bootstrap.popover

import scala.scalajs.js
import js.|

import org.scalajs.dom

@js.native
trait JSPopoverOptions extends js.Object {
  var animation: Boolean         = js.native
  var container: js.Any          = js.native
  var content: String | dom.Node = js.native
  var delay: js.Any              = js.native
  var html: Boolean              = js.native
  var placement: js.Any          = js.native
  var selector: js.Any           = js.native
  var template: String           = js.native
  var title: String              = js.native
  var trigger: String            = js.native
  var viewport: js.Any           = js.native
}
