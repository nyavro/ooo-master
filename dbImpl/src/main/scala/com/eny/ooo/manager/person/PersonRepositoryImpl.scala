package com.eny.ooo.manager.person

import javax.inject.{Inject, Singleton}

import com.eny.ooo.manager.connection.Db
import com.github.mauricio.async.db.RowData
import com.github.mauricio.async.db.general.ArrayRowData
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

  override def update(id:Long, person:Person): Future[Option[Person]] =
    for(
      connection <- db.pool().take;
      res <- connection.sendQuery(s"UPDATE person SET name='${person.name}', middle='${person.middle}', last='${person.last}' WHERE id=$id")
    ) yield {
      if(res.rowsAffected==1) Some(person)
      else None
    }

  override def create(person: Person): Future[Option[Person]] =
    for(
      connection <- db.pool().take;
      insert <- connection.sendQuery(s"INSERT INTO person (name, middle, last) VALUES('${person.name}', '${person.middle}', '${person.last}')");
      id <- if(insert.rowsAffected==1) connection.sendQuery(s"SELECT LAST_INSERT_ID('person')") else Future.failed(new RuntimeException(insert.statusMessage))
    ) yield {
      id.rows.headOption.map(rs => {
        val rs1 = rs(0).asInstanceOf[ArrayRowData]

        println(rs1.length)
        val str: String = rs1.toString()
        println(str)
        val l: Long = str.toLong
        l
      }).map(i => if(i==0) None else Some(i)).map(res => person.copy(id=res))
    }

  private def toPerson(rs:RowData) = Person(Some(rs("id").toString.toLong), rs("name").toString, rs("middle").toString, rs("last").toString)
}
