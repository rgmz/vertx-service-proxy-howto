package io.vertx.howtos.ebservice

import io.vertx.kotlin.core.closeAwait
import io.vertx.kotlin.core.deployVerticleAwait
import io.vertx.kotlin.coroutines.CoroutineVerticle

class MainVerticle : CoroutineVerticle() {

  override suspend fun start() {
    vertx.deployVerticleAwait(BarmanVerticle())
    println("The barman is ready to serve you")
    vertx.deployVerticleAwait(DrunkVerticle())
    vertx.closeAwait()
  }
}
