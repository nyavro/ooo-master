package com.eny.ooo.manager.entity

import play.api.libs.json.Json

object EntityJsonFormat {
  implicit val format = Json.format[Entity]
}