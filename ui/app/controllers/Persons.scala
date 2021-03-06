package controllers

import javax.inject.{Inject, Singleton}

import com.eny.ooo.manager.person.PersonJsonFormat._
import com.eny.ooo.manager.person.{Person, PersonRepository}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future
import scala.util.{Failure, Success}

@Singleton
class Persons @Inject() (repository:PersonRepository) extends Controller {

  def list = Action.async {
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

  def update(id: Long) = Action.async(parse.json) {
    request =>
      request.body.validate[Person].map {
        person =>
          repository.update(id, person).map {
            res => res.map { _ => Created(s"Person Updated")}.getOrElse(BadRequest("Error occured"))
          }
      }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def create() = Action.async(parse.json) {
    request =>
      request.body.validate[Person].map {
        person =>
          repository.create(person).map {
            res => res.map { _ => Created(s"Person Created")}.getOrElse(BadRequest("Error creating person"))
          }
      }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def delete(id: Long) = Action.async {
    repository.delete(id).map {
      case Success(x) => Ok("Succeeded")
      case Failure(x) => BadRequest(x.getMessage)
    }
  }
}
