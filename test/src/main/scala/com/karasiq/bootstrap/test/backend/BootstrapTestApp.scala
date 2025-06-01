package com.karasiq.bootstrap.test.backend

import scala.concurrent.duration._
import scala.io.StdIn
import scala.language.postfixOps

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.util.Timeout

import com.karasiq.bootstrap.test.frontend.TestHtmlPage

object BootstrapTestApp extends App {

  def startup(): Unit = {
    implicit val timeout = Timeout(20 seconds)

    implicit val actorSystem = ActorSystem(Behaviors.empty, "bootstrap-test")
    import actorSystem.executionContext

    Runtime.getRuntime.addShutdownHook(new Thread(() ⇒ actorSystem.terminate()))

    val route =
      get {
        // Server-rendered page
        path("serverside.html") {
          complete(HttpResponse(entity = HttpEntity(ContentTypes.`text/html(UTF-8)`, TestHtmlPage())))
        } ~
          // Index page
          pathEndOrSingleSlash {
            getFromResource("webapp/index.html", ContentTypes.`text/html(UTF-8)`)
          } ~
          // Other resources
          getFromResourceDirectory("webapp")
      }

    val bindingFuture = Http().newServerAt("localhost", 9000).bind(route)

    println(s"Server now online. Please navigate to http://localhost:9000\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind())                      // trigger unbinding from the port
      .onComplete(_ ⇒ actorSystem.terminate()) // and shutdown when done
  }

  startup()
}
