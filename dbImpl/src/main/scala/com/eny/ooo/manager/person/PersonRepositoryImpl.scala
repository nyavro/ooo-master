package com.eny.ooo.manager.person

import javax.inject.{Inject, Singleton}

import com.eny.ooo.manager.connection.Db
import com.github.mauricio.async.db.QueryResult
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class PersonRepositoryImpl @Inject() (db:Db) extends PersonRepository {
  override def load(id: Long): Future[Option[Person]] = {
    Future.successful(Some(Person(Some(1), "Ivan", "Ivan", "Ivan")))
  }

  def list():Future[List[Person]] =
    db.connection().sendQuery("SELECT * FROM person").map {
      qr => qr.rows.head.toList.map {
        rs => Person(Some(rs("id").toString.toLong), rs("name").toString, rs("middle").toString, rs("last").toString)
      }
    }
}
