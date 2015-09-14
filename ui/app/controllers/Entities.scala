package controllers

import javax.inject.{Inject, Singleton}

import com.eny.ooo.manager.entity.EntityJsonFormat._
import com.eny.ooo.manager.entity.{Entity, EntityRepository}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future

@Singleton
class Entities @Inject() (repository:EntityRepository) extends Controller {

  def list(clientId:Long) = Action.async {
    repository.list(clientId)
      .map {
      Json.arr(_)
    }.map {
      items => Ok(items(0))
    }
  }

  def create() = Action.async(parse.json) {
    request =>
      request.body.validate[Entity].map {
        entity =>
          repository.create(entity).map {
            res => res.map { _ => Created(s"Entity Created")}.getOrElse(BadRequest("Error creating Entity"))
          }
      }.getOrElse(Future.successful(BadRequest("Invalid json")))
  }

  def load(id:Long) = Action.async {
    repository.load(id)
      .map {
        case Some(x) => Ok(Json.toJson(x))
        case None => NotFound
      }
  }
}
