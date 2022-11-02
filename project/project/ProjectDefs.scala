//noinspection ScalaPackageName
object ProjectDefs {
  val ScalaJSVersion: String = sys.props.getOrElse("SCALAJS_VERSION", "1.11.0")
  def scalaJSIs06: Boolean   = ScalaJSVersion.startsWith("0.6.")
}
