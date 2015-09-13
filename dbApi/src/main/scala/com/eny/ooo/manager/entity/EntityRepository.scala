package com.eny.ooo.manager.entity

import scala.concurrent.Future
import scala.util.Try

trait EntityRepository {

  def list(clientId:Long):Future[List[Entity]]

  def load(id:Long):Future[Option[Entity]]

  def update(id:Long, entity:Entity):Future[Option[Boolean]]

  def create(entity:Entity):Future[Try[Long]]

  def delete(id: Long):Future[Try[Boolean]]
}