package com.eny.ooo.manager.connection

import com.github.mauricio.async.db.Connection

trait Db {
  def name():String

  def connection():Connection
}
