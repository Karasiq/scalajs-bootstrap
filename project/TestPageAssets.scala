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

  def style: String = {
    """
      |#main-container {
      |    padding: 20px;
      |}
      |
      |@media (min-width: 768px) {
      |    #main-container {
      |        max-width: 80%;
      |    }
      |}
      |
      |td.buttons {
      |    text-align: center;
      |}
      |
      |.panel-primary .panel-head-buttons .glyphicon {
      |    color: white;
      |}
      |
      |.glyphicon {
      |    margin-left: 2px;
      |    margin-right: 2px;
      |}
      |
      |.panel-title .glyphicon {
      |    margin-right: 10px;
      |}
      |
      |#main-container {
      |    margin-top: 50px;
      |}
    """.stripMargin
  }
}