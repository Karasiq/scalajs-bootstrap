package com.karasiq.bootstrap.test.backend

import com.karasiq.bootstrap_text.Bootstrap
import com.karasiq.bootstrap_text.BootstrapImplicits._
import com.karasiq.bootstrap_text.grid.GridSystem
import com.karasiq.bootstrap_text.icons.FontAwesome
import com.karasiq.bootstrap_text.navbar.{NavigationBar, NavigationTab}
import com.karasiq.bootstrap_text.table.{PagedTable, TableRow, TableStyle}
import com.karasiq.bootstrap_text.tooltip.Tooltip

import scala.language.postfixOps
import scalatags.Text.all._
import scalatags.Text.tags2.{style => styleTag, title => titleTag}

object TestHtmlPage {
  def apply(): String = {
    val table = PagedTable(Seq("Number", "Square"), TableRow(Seq(1, 1), Tooltip(b("First row"))) +: (2 to 100).map(i ⇒ TableRow.data(i, i * i)))
      .renderTag(TableStyle.bordered, TableStyle.hover, TableStyle.striped)
    val navigationBar = NavigationBar()
      .withBrand("Scala.js Bootstrap Test", href := "http://getbootstrap.com/components/#navbar")
      .withTabs(
        NavigationTab("Table", "table", "table".fontAwesome(FontAwesome.fixedWidth), table),
        NavigationTab("Empty", "empty", "address-book".fontAwesome(FontAwesome.fixedWidth), Bootstrap.jumbotron("Empty"))
      )
      .withContentContainer(e ⇒ GridSystem.container(GridSystem.mkRow(e), marginTop := 50.px))
      .build()

    "<!doctype html>" + html(head(
      base(href := "/"),
      meta(httpEquiv := "content-type", content := "text/html; charset=utf-8"),
      meta(name := "viewport", content := "width=device-width, initial-scale=1.0"),
      script(src := "//code.jquery.com/jquery-1.12.0.js"),
      raw(bootstrapCdnLinks),
      styleTag(raw(fontAwesomeCss)),
      script(raw(activateTooltipScript)),
      titleTag("Bootstrap text page")
    ), body(
      navigationBar
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
      |@import url('//use.fontawesome.com/releases/v4.7.0/css/font-awesome-css.min.css');
      |@font-face {
      |  font-family: 'FontAwesome';
      |  src: url('//use.fontawesome.com/releases/v4.7.0/fonts/fontawesome-webfont.eot');
      |  src: url('//use.fontawesome.com/releases/v4.7.0/fonts/fontawesome-webfont.eot?#iefix') format('embedded-opentype'),
      |       url('//use.fontawesome.com/releases/v4.7.0/fonts/fontawesome-webfont.woff2') format('woff2'),
      |       url('//use.fontawesome.com/releases/v4.7.0/fonts/fontawesome-webfont.woff') format('woff'),
      |       url('//use.fontawesome.com/releases/v4.7.0/fonts/fontawesome-webfont.ttf') format('truetype'),
      |       url('//use.fontawesome.com/releases/v4.7.0/fonts/fontawesome-webfont.svg#fontawesomeregular') format('svg');
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
