package com.karasiq.bootstrap4.context

import scala.language.postfixOps
import scala.scalajs.js

import org.scalajs.jquery.JQuery

@scalajs.js.native
trait BootstrapJQuery extends scalajs.js.Object {
  def tab(options: scalajs.js.Any): JQuery = scalajs.js.native
  def carousel(options: scalajs.js.Any = ???): JQuery = scalajs.js.native
  def modal(options: scalajs.js.Any = ???): JQuery = scalajs.js.native
  def tooltip(options: scalajs.js.Any = ???): JQuery = scalajs.js.native
  def popover(options: scalajs.js.Any = ???): JQuery = js.native
}
