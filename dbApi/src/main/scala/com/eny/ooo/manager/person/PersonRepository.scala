package com.eny.ooo.manager.person

import scala.concurrent.Future

trait PersonRepository {

  def list():Future[List[Person]]

  def load(id:Long):Future[Option[Person]]
}
