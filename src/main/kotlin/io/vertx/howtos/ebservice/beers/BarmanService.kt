package io.vertx.howtos.ebservice.beers

import io.vertx.codegen.annotations.ProxyGen
import io.vertx.codegen.annotations.VertxGen
import io.vertx.core.AsyncResult
import io.vertx.core.Handler

@VertxGen
@ProxyGen
interface BarmanService {
  fun giveMeARandomBeer(customerName: String, handler: Handler<AsyncResult<Beer>>)

  fun getMyBill(customerName: String, handler: Handler<AsyncResult<Int>>)

  fun payMyBill(customerName: String)
}
