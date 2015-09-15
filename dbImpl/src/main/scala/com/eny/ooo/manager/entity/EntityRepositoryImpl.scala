package com.eny.ooo.manager.entity

import javax.inject.{Inject, Singleton}

import com.eny.ooo.manager.connection.Db
import com.eny.ooo.manager.person.Person
import com.github.mauricio.async.db.RowData

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

@Singleton
class EntityRepositoryImpl @Inject() (db:Db) extends EntityRepository {

  val Table = "entity"
  val Id = "id"
  val ClientId = "client_id"
  val Name = "name"
  val INN = "inn"
  val KPP = "kpp"
  val DirectorId = "director_id"

  override def list(clientId: Long): Future[List[Entity]] =
    for(
      connection <- db.pool().take;
      res <- connection.sendQuery(s"SELECT * FROM $Table WHERE $ClientId=$clientId")
    ) yield res.rows.head.toList.map(toEntity)

  override def update(id: Long, entity: Entity): Future[Option[Boolean]] = ???

  override def delete(id: Long) = {
    for(
      connection <- db.pool().take;
      delete <- connection.sendQuery(s"DELETE FROM $Table WHERE id=$id")
    ) yield delete.rowsAffected match {
      case 1 => Success(true)
      case _ => Failure(new RuntimeException(delete.statusMessage))
    }
  }

  override def load(id: Long): Future[Option[Entity]] =
    for(
      connection <- db.pool().take;
      res <- connection.sendQuery(s"SELECT * FROM $Table WHERE $Id=$id")
    ) yield res.rows.head.headOption.map(toEntity)

  //insert into entity (client_id, inn, kpp, name, director_id) values (1, 1234567890, 111222333, "Horns", 15);
  override def create(entity: Entity): Future[Try[Long]] =
    for(
      connection <- db.pool().take;
      insert <- connection.sendQuery(s"INSERT INTO $Table($ClientId, $INN, $KPP, $Name, $DirectorId) VALUES(${entity.clientId}, ${entity.inn.orNull}, ${entity.kpp.orNull}, '${entity.name}', '${entity.directorId}')");
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

  def toEntity(rs:RowData) = Entity(
    rs(ClientId).toString.toLong,
    Some(rs(Id).toString.toLong),
    rs(Name).toString,
    asOption(rs(INN)).map(_.toString.toLong),
    asOption(rs(KPP)).map(_.toString.toLong),
    rs(DirectorId).toString.toLong
  )

  def asOption(ref:Any) = if(ref==null) None else Some(ref)
}
