package com.eny.ooo.manager.person

import javax.inject.{Inject, Singleton}

import com.eny.ooo.manager.connection.Db
import com.github.mauricio.async.db.RowData

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

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

  override def update(id:Long, person:Person): Future[Option[Person]] =
    for(
      connection <- db.pool().take;
      res <- connection.sendQuery(s"UPDATE person SET name='${person.name}', middle='${person.middle}', last='${person.last}' WHERE id=$id")
    ) yield {
      if(res.rowsAffected==1) Some(person)
      else None
    }

  override def create(person: Person): Future[Try[Long]] =
    for(
      connection <- db.pool().take;
      insert <- connection.sendQuery(s"INSERT INTO person (name, middle, last) VALUES('${person.name}', '${person.middle}', '${person.last}')");
      idTry <-
        if(insert.rowsAffected==1)
          connection
            .sendQuery(s"SELECT LAST_INSERT_ID()")
            .map(_.rows)
            .map {
            case Some(x) => Success(x.head(0).toString.toLong)
            case None => Failure(new RuntimeException(insert.statusMessage))
          }
        else
          Future.successful(Failure(new RuntimeException(insert.statusMessage)))
    ) yield idTry

  override def delete(id:Long) = {
    for(
      connection <- db.pool().take;
      delete <- connection.sendQuery(s"DELETE FROM person WHERE id=$id")
    ) yield delete.rowsAffected match {
      case 1 => Success(true)
      case _ => Failure(new RuntimeException(delete.statusMessage))
    }
  }

  private def toPerson(rs:RowData) = Person(Some(rs("id").toString.toLong), rs("name").toString, rs("middle").toString, rs("last").toString)
}
