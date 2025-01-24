//noinspection ScalaPackageName
object ProjectDefs {
  val ScalaJSVersion: String = sys.props.getOrElse("SCALAJS_VERSION", "1.18.2")
  def scalaJSIs06: Boolean   = ScalaJSVersion.startsWith("0.6.")
}
