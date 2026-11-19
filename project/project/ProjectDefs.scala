//noinspection ScalaPackageName
object ProjectDefs {
  val ScalaJSVersion: String = sys.props.getOrElse("SCALAJS_VERSION", "1.23.0")
  def scalaJSIs06: Boolean   = ScalaJSVersion.startsWith("0.6.")
}
