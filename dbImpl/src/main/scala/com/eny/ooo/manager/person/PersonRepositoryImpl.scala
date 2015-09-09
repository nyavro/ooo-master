package com.eny.ooo.manager.person

import javax.inject.{Inject, Singleton}

import com.eny.ooo.manager.connection.Db
import com.github.mauricio.async.db.RowData

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class PersonRepositoryImpl @Inject() (db:Db) extends PersonRepository {

  override def load(id:Long): Future[Option[Person]] =
    for(
      connection <- db.pool().take;
      res <- connection.sendQuery(s"SELECT * FROM person WHERE id=$id")
    ) yield res.rows.head.headOption.map(toPerson)

  override def list():Future[List[Person]] =
    for(
      connection <- db.pool().take;
      res <- connection.sendQuery("SELECT * FROM person")
    ) yield res.rows.head.toList.map(toPerson)

  override def update(id:Long, person:Person): Future[Option[Person]] = {
    for(
      connection <- db.pool().take;
      res <- connection.sendQuery(s"UPDATE person set name=${person.name}, middle=${person.middle}, last=${person.last} WHERE id=${person.id}")
    ) yield res.rows.head.headOption.map(toPerson)
  }

  private def toPerson(rs:RowData) = Person(Some(rs("id").toString.toLong), rs("name").toString, rs("middle").toString, rs("last").toString)
}
