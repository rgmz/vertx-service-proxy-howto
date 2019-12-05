package io.vertx.howtos.ebservice

import io.vertx.howtos.ebservice.beers.BarmanServiceVertxEBProxy
import io.vertx.howtos.ebservice.beers.Beer
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.awaitEvent
import io.vertx.kotlin.coroutines.awaitResult

class DrunkVerticle : CoroutineVerticle() {

  override suspend fun start() {
    val service = BarmanServiceVertxEBProxy(vertx, "beers.services.myapplication")

    val beer = awaitResult<Beer> { service.giveMeARandomBeer("homer", it) }
    println("My first beer is a $beer and it costs $${beer.price}")

    awaitEvent<Long> { vertx.setTimer(1500, it) }
    val newBeer = awaitResult<Beer> { service.giveMeARandomBeer("homer", it) }
    println("My second beer is a $newBeer and it costs $${newBeer.price}")

    val bill = awaitResult<Int> { service.getMyBill("homer", it) }
    println("My bill with the bar is $bill")
    service.payMyBill("homer")
  }
}
