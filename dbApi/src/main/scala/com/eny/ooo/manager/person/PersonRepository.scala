package com.eny.ooo.manager.person

import scala.concurrent.Future

trait PersonRepository {
  def load(id:Long):Future[Option[Person]]
}
