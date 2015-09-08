package com.eny.ooo.manager.connection

import com.github.mauricio.async.db.mysql.MySQLConnection
import com.github.mauricio.async.db.pool.ConnectionPool

trait Db {
  def pool():ConnectionPool[MySQLConnection]
}
