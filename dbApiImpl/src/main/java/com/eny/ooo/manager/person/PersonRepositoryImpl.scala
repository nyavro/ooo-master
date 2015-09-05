package com.eny.ooo.manager.person

import javax.inject.Singleton

import scala.concurrent.Future

@Singleton
class PersonRepositoryImpl extends PersonRepository {
  override def load(id: Long): Future[Option[Person]] = {
    Future.successful(Some(Person(Some(1), "Ivan")))
  }

  def list():Future[List[Person]] = Future.successful(List(Person(Some(1), "Ivan"), Person(Some(2), "Petr")))
}
