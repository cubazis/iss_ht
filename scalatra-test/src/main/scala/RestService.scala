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

        println("***** Supported operations *****")
        println("List users:   curl -v http://localhost:8080/users")
        println("User details: curl -v http://localhost:8080/users/<id>")
        println("Create user:  curl -v http://localhost:8080/users -X POST -d '{\"name\": \"<someName>\"}' -H 'Content-type: application/json'")
        println("Update user:  curl -v http://localhost:8080/users/<id> -X PUT -d '{\"name\": \"<newName>\"}' -H 'Content-type: application/json'")
        println("Delete user:  curl -v http://localhost:8080/users/<id> -X DELETE")
        println("********************************")

        server.join()
}
