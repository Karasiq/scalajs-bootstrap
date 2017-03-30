package com.karasiq.bootstrap.utils

import org.scalajs.jquery.JQuery

import scala.language.postfixOps
import scala.scalajs.js

@scalajs.js.native
trait BootstrapJQuery extends scalajs.js.Object {
  def tab(options: js.Any): JQuery = scalajs.js.native
  def carousel(options: js.Any = ???): JQuery = js.native
  def modal(options: js.Any = ???): JQuery = js.native
  def tooltip(options: js.Any = ???): JQuery = js.native
  def popover(options: js.Any = ???): JQuery = js.native
}
