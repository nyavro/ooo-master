package controllers

import javax.inject.{Inject, Singleton}

import com.eny.ooo.manager.person.PersonJsonFormat._
import com.eny.ooo.manager.person.PersonRepository
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.mvc.{Action, Controller}

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
}
