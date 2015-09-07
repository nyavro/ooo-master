package com.eny.ooo.manager.connection

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Named, Singleton}

import com.github.mauricio.async.db.Configuration
import com.github.mauricio.async.db.mysql.MySQLConnection

import scala.concurrent.Await
import scala.concurrent.duration.Duration

@Singleton
class DbImpl @Inject() (
     @Named("db.user")user:String,
     @Named("db.host")host:String,
     @Named("db.port")port:Int,
     @Named("db.password")password:String,
     @Named("db.database")db:String
   ) extends Db {
  val name = user

  val configuration = new Configuration(user, host, port, if(password.equals("")) None else Some(password), Some(db))

  val connection = new MySQLConnection(configuration)

  Await.result(connection.connect, Duration(5, TimeUnit.SECONDS))
}