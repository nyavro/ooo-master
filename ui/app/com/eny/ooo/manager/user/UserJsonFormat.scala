package com.eny.ooo.manager.user
import play.api.libs.json.Json

object UserJsonFormat {
  implicit val userFormat = Json.format[User]
}