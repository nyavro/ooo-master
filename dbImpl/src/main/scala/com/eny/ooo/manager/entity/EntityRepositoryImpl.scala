package com.eny.ooo.manager.entity

import javax.inject.{Inject, Singleton}

import com.eny.ooo.manager.connection.Db
import com.github.mauricio.async.db.RowData

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Try

@Singleton
class EntityRepositoryImpl @Inject() (db:Db) extends EntityRepository {

  val Table = "entity"
  val ClientId = "client_id"

  override def list(clientId: Long): Future[List[Entity]] =
    for(
      connection <- db.pool().take;
      res <- connection.sendQuery(s"SELECT * FROM $Table WHERE $ClientId=$clientId")
    ) yield res.rows.head.toList.map(toEntity)

  override def update(id: Long, entity: Entity): Future[Option[Boolean]] = ???

  override def delete(id: Long): Future[Try[Boolean]] = ???

  override def load(id: Long): Future[Option[Entity]] = ???

  override def create(entity: Entity): Future[Try[Long]] = ???

  def toEntity(rs:RowData) = Entity(Some(rs("id").toString.toLong), rs("name").toString)
}
