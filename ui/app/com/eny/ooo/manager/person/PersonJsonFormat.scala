package com.eny.ooo.manager.person

import play.api.libs.json.Json

object PersonJsonFormat {
  implicit val format = Json.format[Person]
}