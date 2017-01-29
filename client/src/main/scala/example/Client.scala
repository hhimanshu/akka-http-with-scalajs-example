package example

import org.scalajs.dom
import org.scalajs.dom.ext.Ajax
import shared.SharedMessages.ToDoItem

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._


@JSExport
object Client extends js.JSApp {
  @JSExport
  def main(): Unit = {
    import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow

    dom.document.body.appendChild(
      div(
        h1("Hello World!"),
        p("I am happy to be running on Full Stack Scala")
      ).render
    )

    Ajax.get("/todo").onSuccess { case xhr =>
      dom.document.body.appendChild(
        div(
          h4("Server Message"),
          p (xhr.responseText)
        ).render
      )
    }

    val todoItem = ToDoItem("straight from client")
    dom.document.body.appendChild(
      div(
        h4("Client Message"),
        p (todoItem.desc)
      ).render
    )
  }
}