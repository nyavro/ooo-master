package com.eny.ooo.manager.configuration

import java.util.Properties

import com.eny.ooo.manager.connection.{Db, DbImpl}
import com.eny.ooo.manager.person.{PersonRepository, PersonRepositoryImpl}
import com.google.inject.AbstractModule
import com.google.inject.name.Names

import scala.collection.JavaConversions._

class Configuration extends AbstractModule {
  override def configure() = {
    bind(classOf[Db]).to(classOf[DbImpl])
    bind(classOf[PersonRepository]).to(classOf[PersonRepositoryImpl])
    val props = new Properties
    props.load(getClass.getClassLoader.getResourceAsStream("db.properties"))
    props.keySet().map { key =>
      bindConstant().annotatedWith(Names.named(key.toString)).to(props.getProperty(key.toString))
    }
  }
}
