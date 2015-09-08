package com.eny.ooo.manager.connection

import javax.inject.{Inject, Named, Singleton}

import com.github.mauricio.async.db.Configuration
import com.github.mauricio.async.db.mysql.MySQLConnection
import com.github.mauricio.async.db.mysql.pool.MySQLConnectionFactory
import com.github.mauricio.async.db.pool.{ConnectionPool, PoolConfiguration}

@Singleton
class DbImpl @Inject() (
     @Named("db.user")user:String,
     @Named("db.host")host:String,
     @Named("db.port")port:Int,
     @Named("db.password")password:String,
     @Named("db.database")db:String
   ) extends Db {

  val configuration = new Configuration(user, host, port, if(password.equals("")) None else Some(password), Some(db))

  override def pool() =
    new ConnectionPool[MySQLConnection](new MySQLConnectionFactory(configuration), PoolConfiguration.Default)

}
