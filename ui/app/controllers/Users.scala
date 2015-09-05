package controllers

import javax.inject.Singleton

import org.slf4j.{Logger, LoggerFactory}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.mvc._
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection

import scala.concurrent.Future

@Singleton
class Users extends Controller with MongoController {

  private final val logger: Logger = LoggerFactory.getLogger(classOf[Users])

  def collection: JSONCollection = db.collection[JSONCollection]("users")

  import com.eny.ooo.manager.user.UserJsonFormat._
  import com.eny.ooo.manager.user._

  def createUser = Action.async(parse.json) {
    request =>
      request.body.validate[User].map {
        user =>
          collection.insert(user).map {
            lastError =>
              logger.debug(s"Successfully inserted with LastError: $lastError")
              Created(s"User Created")
          }
      }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def updateUser(firstName: String, lastName: String) = Action.async(parse.json) {
    request =>
      request.body.validate[User].map {
        user =>
          collection.update(Json.obj("firstName" -> firstName, "lastName" -> lastName), user).map {
            lastError =>
              logger.debug(s"Successfully updated with LastError: $lastError")
              Created(s"User Updated")
          }
      }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def findUsers = Action.async {
    collection
      .find(Json.obj("active" -> true))
      .sort(Json.obj("created" -> -1))
      .cursor[User].collect[List]()
      .map {
        Json.arr(_)
      }.map {
        users => Ok(users(0))
      }
  }

}
