package com.karasiq.bootstrap.test.frontend

import com.karasiq.bootstrap.Bootstrap.text._
import rx._

import scala.language.postfixOps
import scalaTags.all._

class TestTextContainer extends BootstrapComponent {
  def render(md: ModifierT*): ModifierT = {
    val testModal = this.createModal
    val rxText = Var("ERROR") // Pseudo-reactive binding
    val navigationBar = NavigationBar()
      .withBrand(rxText, href := "http://getbootstrap.com/components/#navbar")
      .withTabs(
        NavigationTab("Table", "table", "table".fontAwesome(FontAwesome.fixedWidth), this.createTable),
        NavigationTab("Carousel", "carousel", "picture".glyphicon, this.createCarousel),
        NavigationTab("Buttons", "empty", "address-book".fontAwesome(FontAwesome.fixedWidth), Bootstrap.jumbotron(
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
    val imgSrc = "https://upload.wikimedia.org/wikipedia/commons/9/9e/Scorpius_featuring_Mars_and_Saturn._%2828837147345%29.jpg"
    Carousel(
      Carousel.slide(
        imgSrc,
        h3("First slide label"),
        p("Nulla vitae elit libero, a pharetra augue mollis interdum.")
      ),
      Carousel.slide(
        imgSrc,
        h3("Second slide label"),
        p("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
      )
    )
  }
}

object TestTextPage {
  def apply(): String = {
    "<!doctype html>" + html(head(
      base(href := "/"),
      meta(httpEquiv := "content-type", content := "text/html; charset=utf-8"),
      meta(name := "viewport", content := "width=device-width, initial-scale=1.0"),
      script(src := "https://code.jquery.com/jquery-1.12.0.js"),
      raw(bootstrapCdnLinks),
      scalaTags.tags2.style(raw(fontAwesomeCss)),
      script(raw(activateTooltipScript)),
      scalaTags.tags2.title("Bootstrap text page")
    ), body(
      new TestTextContainer
    ))
  }

  private[this] def bootstrapCdnLinks: String =
    """
      |<!-- Latest compiled and minified CSS -->
      |<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
      |
      |<!-- Optional theme -->
      |<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
      |
      |<!-- Latest compiled and minified JavaScript -->
      |<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
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
