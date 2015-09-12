package com.eny.ooo.manager.person

import scala.concurrent.Future
import scala.util.Try

trait PersonRepository {

  def list():Future[List[Person]]

  def load(id:Long):Future[Option[Person]]

  def update(id:Long, person:Person):Future[Option[Person]]

  def create(person:Person):Future[Try[Long]]

  def delete(id: Long):Future[Try[Boolean]]
}
