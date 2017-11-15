package com.karasiq.bootstrap4.test.frontend

import scala.language.postfixOps

import rx._

import com.karasiq.bootstrap4.Bootstrap.text._
import scalaTags.all._

object TestHtmlPage {
  def apply(): String = {
    "<!doctype html>" + html(head(
      base(href := "/"),
      meta(httpEquiv := "content-type", content := "text/html; charset=utf-8"),
      meta(name := "viewport", content := "width=device-width, initial-scale=1.0"),
      script(src := "https://code.jquery.com/jquery-3.2.1.js"),
      raw(bootstrapCdnLinks),
      scalaTags.tags2.style(raw(fontAwesomeCss)),
      script(raw(activateTooltipScript)),
      scalaTags.tags2.title("Bootstrap text page")
    ), body(
      new TestContainer
    ))
  }

  private[this] class TestContainer extends BootstrapComponent {
    def render(md: ModifierT*): ModifierT = {
      val testModal = this.createModal
      val rxText = Var("ERROR") // Pseudo-reactive binding
      val navigationBar = NavigationBar()
        .withBrand(rxText, href := "http://getbootstrap.com/components/#navbar")
        .withTabs(
          NavigationTab("Table", "table", "table".faFwIcon, this.createTable),
          NavigationTab("Carousel", "carousel", "file-image-o".faFwIcon, this.createCarousel),
          NavigationTab("Buttons", "empty", "address-book".faFwIcon, Bootstrap.jumbotron(
            Bootstrap.button("Modal", testModal.toggle)
          ))
        )
        .withContentContainer(e ⇒ GridSystem.container(GridSystem.mkRow(e), marginTop := 60.px))
        .build()
      rxText() = "Scala.js Bootstrap Test"
      Seq[ModifierT](testModal, navigationBar)
    }

    private[this] def createTable = {
      val table = PagedTable(Rx(Seq("Number", "Square")), Rx(TableRow(Seq(1, 1), Tooltip(b("First row")),
        onclick := Callback.onClick(_ ⇒ println("Pseudo callback"))) +: (2 to 100).map(i ⇒ TableRow.data(i, i * i))))
      table.renderTag(TableStyle.bordered, TableStyle.hover, TableStyle.striped)
    }

    private[this] def createModal = {
      Modal()
        .withBody("Text-rendered modal")
    }

    private[this] def createCarousel = {
      TestCarousel("https://upload.wikimedia.org/wikipedia/commons/9/9e/Scorpius_featuring_Mars_and_Saturn._%2828837147345%29.jpg")
    }
  }

  private[this] def bootstrapCdnLinks: String =
    """
      |<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
      |<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
      |<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
      |""".stripMargin

  private[this] def fontAwesomeCss: String =
    """
      |/*!
      | *  Font Awesome v4.7.0 by @davegandy - http://fontawesome.io - @fontawesome
      | *  License - http://fontawesome.io/license (Font: SIL OFL 1.1, CSS: MIT License)
      | */
      |@import url('https://use.fontawesome.com/releases/v4.7.0/css/font-awesome-css.min.css');
      |@font-face {
      |  font-family: 'FontAwesome';
      |  src: url('https://use.fontawesome.com/releases/v4.7.0/fonts/fontawesome-webfont.eot');
      |  src: url('https://use.fontawesome.com/releases/v4.7.0/fonts/fontawesome-webfont.eot?#iefix') format('embedded-opentype'),
      |       url('https://use.fontawesome.com/releases/v4.7.0/fonts/fontawesome-webfont.woff2') format('woff2'),
      |       url('https://use.fontawesome.com/releases/v4.7.0/fonts/fontawesome-webfont.woff') format('woff'),
      |       url('https://use.fontawesome.com/releases/v4.7.0/fonts/fontawesome-webfont.ttf') format('truetype'),
      |       url('https://use.fontawesome.com/releases/v4.7.0/fonts/fontawesome-webfont.svg#fontawesomeregular') format('svg');
      |  font-weight: normal;
      |  font-style: normal;
      |}
    """.stripMargin

  private[this] def activateTooltipScript: String =
    """
      |$(function () {
      |  $('[data-toggle="tooltip"]').tooltip()
      |})
    """.stripMargin
}
