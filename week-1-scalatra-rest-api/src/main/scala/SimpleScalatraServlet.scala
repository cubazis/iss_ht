import org.json4s.{DefaultFormats, Formats, _}
import org.scalatra._
import org.scalatra.json._


class SimpleScalatraServlet extends ScalatraServlet with JacksonJsonSupport {

        case class Message(id: Int, text: String)

        case class CreateMessage(text: String)
        case class MessageCreated(id: Int)

        case class UpdateMessage(text: String)
        case class MessageUpdated(index: Int)

        protected implicit val jsonFormats: Formats = DefaultFormats

        //This collection represents a simple in-memory data source (i.e. it is mutable and not thread-safe)
        var messages: List[Message]  = List(
                Message(1, "Scala is amazing!"),
                Message(2, "Hate Java, but Scala is the Lamb of God"),
                Message(3, "I like cookies"))

        before() {
                contentType = formats("json")
        }

        get("/") {
                "The page you requested cannot be found right meow. Try typing 'kittens'."
        }

        get("/messages") {
                messages
        }

        get("/messages/") {
                messages
        }

        get("/messages/:id") {
                messages.find(m => m.id == params("id").toInt) match {
                        case Some(message) => Ok(message)
                        case None       => NotFound("Message not found")
                }
        }

        post("/messages") {
                //val messageData = parsedBody.extract[CreateMessage]
                Ok(parsedBody.extract[CreateMessage])
                /*val id = messages.foldLeft (0) ( (maxId, u) => if (u.id > maxId) u.id else maxId ) + 1

                val newMsg = Message(id, messageData.text)
                messages = newMsg :: messages

                MessageCreated(id)*/
        }

        put("/messages/:id") {
                val id = params("id").toInt
                val matchingIndex = messages.zipWithIndex.collect { case (Message(`id`, _), i) => i }
                if (matchingIndex.isEmpty)
                        NotFound("Message not found")
                else {
                        val index = matchingIndex.head
                        val newMessageData = parsedBody.extract[UpdateMessage]

                        messages = messages.updated(index, Message(id, newMessageData.text))

                        Ok(MessageUpdated(index))
                }
        }

        delete("/messages/:id") {
                messages = messages.filter( u => u.id != params("id").toInt)
                Ok("Done")
        }
}