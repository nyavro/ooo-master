package com.eny.ooo.manager.entity

import javax.inject.Singleton

import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class Views extends Controller {
  def list = Action.async {
    Future(Ok(views.html.entity.list()))
  }

  def create = Action.async {
    Future(Ok(views.html.entity.create()))
  }
}
