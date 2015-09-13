package controllers

import javax.inject.{Inject, Singleton}

import com.eny.ooo.manager.entity.EntityJsonFormat._
import com.eny.ooo.manager.entity.EntityRepository
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.mvc.{Action, Controller}

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

}
