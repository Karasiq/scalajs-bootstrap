object ProjectDefs {
  val ScalaJSVersion = sys.env.getOrElse("SCALAJS_VERSION", "1.8.0")
  def scalaJSIs06    = ScalaJSVersion.startsWith("0.6.")
}
