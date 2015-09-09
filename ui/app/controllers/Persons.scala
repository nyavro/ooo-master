package controllers

import javax.inject.{Inject, Singleton}

import com.eny.ooo.manager.person.PersonJsonFormat._
import com.eny.ooo.manager.person.{Person, PersonRepository}
import com.eny.ooo.manager.user.User
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future

@Singleton
class Persons @Inject() (repository:PersonRepository) extends Controller {
  def find = Action.async {
    repository.list()
      .map {
        Json.arr(_)
      }.map {
        items => Ok(items(0))
      }
  }

  def load(id:Long) = Action.async {
    repository.load(id)
      .map {
        case Some(x) => Ok(Json.toJson(x))
        case None => NotFound
      }
  }

//  def updatePerson(id: Long) = Action.async(parse.json) {
//    request =>
//      request.body.validate[Person].map {
//        user =>
//          repository.update(id, user).map {
//            res =>
//              res match {
//                case Some(x) => Created(s"Person Updated")
//                case None => BadRequest("Error occured")
//              }
//          }
//      }
//  }
}
