package com.eny.ooo.manager.person

import javax.inject.{Inject, Singleton}

import com.eny.ooo.manager.connection.Db

import scala.concurrent.Future

@Singleton
class PersonRepositoryImpl @Inject() (db:Db) extends PersonRepository {
  override def load(id: Long): Future[Option[Person]] = {
    Future.successful(Some(Person(Some(1), "Ivan")))
  }

  def list():Future[List[Person]] = Future.successful(List(Person(Some(1), "Ivanidze"), Person(Some(2), "Petridze"), Person(Some(3), db.name())))
}
