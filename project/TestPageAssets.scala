import scalatags.Text.all._

object TestPageAssets {
  def index: String = {
    "<!DOCTYPE html>" + html(
      head(
        base(href := "/"),
        meta(name := "viewport", content := "width=device-width, initial-scale=1.0")
      ),
      body(
        // Empty
      )
    )
  }
}