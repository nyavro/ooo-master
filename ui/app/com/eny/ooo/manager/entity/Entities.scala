package com.eny.ooo.manager.entity

import javax.inject.{Inject, Singleton}

import com.eny.ooo.manager.entity.EntityJsonFormat._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future
import scala.util.{Failure, Success}

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
    implicit request =>
      form.bindFromRequest.fold(
        formWithErrors => Future.successful(BadRequest("Error occured")),
        entity =>
          repository.create(entity).map {
            res => res.map { _ => Created(s"Entity Created")}.getOrElse(BadRequest("Error creating Entity"))
          }
      )
  }

  def load(id:Long) = Action.async {
    repository.load(id)
      .map {
        case Some(x) => Ok(Json.toJson(x))
        case None => NotFound
      }
  }

  def delete(id: Long) = Action.async {
    repository.delete(id).map {
      case Success(x) => Ok("Succeeded")
      case Failure(x) => BadRequest(x.getMessage)
    }
  }

  val form = Form(
    mapping(
      "clientId" -> longNumber,
      "id" -> optional(longNumber),
      "name" -> text.verifying(nonEmpty),
      "inn" -> optional(longNumber),
      "kpp" -> optional(longNumber),
      "directorId" -> longNumber
    )(Entity.apply)(Entity.unapply)
  )

  def update(id: Long) = Action.async(parse.json) {
    implicit request =>
      form.bindFromRequest.fold(
        formWithErrors => Future.successful(BadRequest("Error occured")),
        entity => repository.update(id, entity).map {
          res => res.map { _ => Created(s"Entity Updated")}.getOrElse(BadRequest("Error occured"))
        }
      )
  }
}
