package example

import akka.http.scaladsl.server.{Directives, Route}
import com.typesafe.config.ConfigFactory

object Config {
  private val c = ConfigFactory.load().getConfig("todoMvc")
  val productionMode: Boolean = c.getBoolean("productionMode")
}

class WebService() extends Directives {

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
      }
  }
}
