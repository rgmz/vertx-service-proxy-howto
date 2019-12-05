package io.vertx.howtos.ebservice.beers.impl

import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.client.WebClient
import io.vertx.howtos.ebservice.beers.BarmanService
import io.vertx.howtos.ebservice.beers.Beer
import io.vertx.kotlin.core.json.get
import java.util.*

class BarmanServiceImpl(private val webClient: WebClient) : BarmanService {
  private val bills = mutableMapOf<String, Int>()
  private val random: Random = Random()

  override fun giveMeARandomBeer(customerName: String, handler: Handler<AsyncResult<Beer>>) {
    webClient.get(443, "www.craftbeernamegenerator.com", "/api/api.php?type=classic")
      .ssl(true)
      .send {
        if (it.failed()) {
          handler.handle(Future.failedFuture(it.cause()))
        } else {
          val result = it.result().bodyAsJsonObject()
          if (result.get<Int>("status") != 200) {
            handler.handle(
              Future.failedFuture(
                "Beer Generator Service replied with ${result.get<Int>("status")}: ${result.get<String>("status_message")}"
              )
            )
          } else {
            val beer = Beer().apply {
              name = result.get<JsonObject>("data")["name"]
              style = result.get<JsonObject>("data")["style"]
              price = 3 + random.nextInt(5)
            }
            println("Generated a new Beer! $beer")
            bills.merge(customerName, beer.price) { oldVal, newVal -> oldVal + newVal }
            handler.handle(Future.succeededFuture(beer))
          }
        }
      }
  }

  override fun getMyBill(customerName: String, handler: Handler<AsyncResult<Int>>) {
    handler.handle(Future.succeededFuture(bills[customerName]))
  }

  override fun payMyBill(customerName: String) {
    bills.remove(customerName)
    println("Removed debt of $customerName")
  }
}
