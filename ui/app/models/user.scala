package models

case class User( age: Int,
                 firstName: String,
                 lastName: String,
                 active: Boolean)

object JsonFormats {
  import play.api.libs.json.Json

  implicit val userFormat = Json.format[User]
}