package com.karasiq.bootstrap

import com.karasiq.bootstrap.buttons.ButtonBuilder

import scalatags.JsDom.all._

object Bootstrap {
  /**
    * Default button
    */
  def button: Tag = ButtonBuilder().build

  /**
    * Glyphicon
    * @param name Icon name
    * @see [[https://getbootstrap.com/components/#glyphicons]]
    */
  def icon(name: String): Tag = {
    span(`class` := s"glyphicon glyphicon-$name", aria.hidden := true)
  }

  private var idCounter: Int = 0

  /**
    * Generates unique element ID
    */
  def newId: String = {
    idCounter = idCounter + 1
    s"bs-auto-$idCounter"
  }
}
