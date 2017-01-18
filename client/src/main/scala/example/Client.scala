package example

import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._


@JSExport
object Client extends js.JSApp {
  @JSExport
  def main() = {
    dom.document.body.appendChild(
      div(
        h1("Hello World!")
      ).render
    )
  }
}