package io.vertx.howtos.ebservice.beers

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.get

@DataObject(generateConverter = true)
class Beer() {
  lateinit var name: String
  lateinit var style: String
  var price: Int = 0

  constructor(json: JsonObject) : this() {
    name = json["name"] ?: ""
    style = json["style"] ?: ""
    price = json["price"] ?: 0
  }

  fun toJson(): JsonObject = JsonObject.mapFrom(this)
}
