package controllers

import javax.inject.Singleton

import com.eny.ooo.manager.person.Person
import com.eny.ooo.manager.person.PersonJsonFormat._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future

@Singleton
class Persons extends Controller {
  def find = Action.async {
    val successful: Future[List[Person]] = Future.successful(List(Person(Some(1), "Ivan"), Person(Some(2), "Petr")))
    successful
      .map {
        Json.arr(_)
      }.map {
        items => Ok(items(0))
      }
  }
}
