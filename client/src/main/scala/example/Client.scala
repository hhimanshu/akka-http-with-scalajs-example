package example

import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._


@JSExport
object Client extends js.JSApp {
  @JSExport
  def main(): Unit = {
    val host = sys.env.getOrElse("APP_URL", "localhost")

    dom.document.body.appendChild(
      div(
        h1("Hello World!"),
        p("I am happy to be running on Full Stack Scala"),
        p (s"I am running on $host")

      ).render
    )
  }
}