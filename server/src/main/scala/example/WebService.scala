package example

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.{Directives, Route}
import com.typesafe.config.ConfigFactory
import shared.SharedMessages.ToDoItem
import spray.json._

object Config {
  private val c = ConfigFactory.load().getConfig("todoMvc")
  val productionMode: Boolean = c.getBoolean("productionMode")
}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val todoItemFormat: RootJsonFormat[ToDoItem] = jsonFormat1(ToDoItem)
}


class WebService() extends Directives with JsonSupport {
  val route: Route = {
    pathSingleSlash {
      get {
        if (Config.productionMode) getFromResource("web/index-full.html")
        else getFromResource("web/index.html")
      }
    } ~
      pathPrefix("assets" / Remaining) { file =>
        // optionally compresses the response with Gzip or Deflate
        // if the client accepts compressed responses
        encodeResponse {
          getFromResource("public/" + file)
        }
      } ~ path("todo") {
        complete(ToDoItem("Coming from Server"))
      }
  }
}
