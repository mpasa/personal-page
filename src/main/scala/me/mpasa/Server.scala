package me.mpasa

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Success, Try}

object Server {

  implicit val system: ActorSystem = ActorSystem("my-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  def main(args: Array[String]): Unit = {
    Try(args(0).toInt) match {
      case Success(port) =>
        val host: String = "localhost"
        Http().bindAndHandle(Router.routes, host, port)
        println(s"Server online at $host ($port)")
      case _ =>
        println("You must specify the port as the first argument of the app")
    }
  }
}
