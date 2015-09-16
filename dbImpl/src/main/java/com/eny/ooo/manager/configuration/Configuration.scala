package com.eny.ooo.manager.configuration

import com.eny.ooo.manager.person.{PersonRepository, PersonRepositoryImpl}
import com.google.inject.AbstractModule

class Configuration extends AbstractModule {
  override def configure() = {
    bind(classOf[PersonRepository]).to(classOf[PersonRepositoryImpl])
  }
}
