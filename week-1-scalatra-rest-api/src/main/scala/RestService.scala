import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext

import org.scalatra.servlet.ScalatraListener

object RestService extends App {
        val port = 8080
        val server = new Server(port)
        val context = new WebAppContext()
        context.setContextPath("/")
        context.setResourceBase(".")
        context.setInitParameter(ScalatraListener.LifeCycleKey, "ScalatraBootstrap")
        context.setEventListeners(Array(new ScalatraListener))

        server.setHandler(context)
        server.start()
        server.join()
}
