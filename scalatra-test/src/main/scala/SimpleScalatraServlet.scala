import org.json4s._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json._

case class User(id: Int, name: String)

case class CreateUser(name: String)
case class UserCreated(id: Int)

case class UpdateUser(name: String)
case class UserUpdated(index: Int)

class SimpleScalatraServlet extends ScalatraServlet with JacksonJsonSupport {



        protected implicit lazy val jsonFormats: Formats = DefaultFormats

        //This collection represents a simple in-memory data source (i.e. it is mutable and not thread-safe)
        var users: List[User]  = List(User(1, "John"), User(2, "Todd"), User(3, "Chris"))

        before() {
                contentType = formats("json")
        }

        get("/") {
                "The page you requested cannot be found right meow. Try typing 'kittens'."
        }

        get("/users") {
                users
        }

        get("/users/") {
                users
        }

        get("/users/:id") {
                users.find(u => u.id == params("id").toInt) match {
                        case Some(user) => Ok(user)
                        case None       => NotFound("User not found")
                }
        }

        post("/users") {
                val userData = parsedBody.extract[CreateUser]
                val id = users.foldLeft (0) ( (maxId, u) => if (u.id > maxId) u.id else maxId ) + 1

                users = User(id, userData.name) :: users

                UserCreated(id)
        }

        put("/users/:id") {
                val id = params("id").toInt
                val matchingIndex = users.zipWithIndex.collect { case (User(`id`, _), i) => i }
                if (matchingIndex.isEmpty)
                        NotFound("User not found")
                else {
                        val index = matchingIndex.head
                        val newUserData = parsedBody.extract[UpdateUser]

                        users = users.updated(index, User(id, newUserData.name))

                        Ok(UserUpdated(index))
                }
        }

        delete("/users/:id") {
                users = users.filter( u => u.id != params("id").toInt)
                Ok("Done")
        }
}
