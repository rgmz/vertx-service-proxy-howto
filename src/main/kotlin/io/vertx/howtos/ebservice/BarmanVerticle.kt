package io.vertx.howtos.ebservice

import io.vertx.ext.web.client.WebClient
import io.vertx.howtos.ebservice.beers.BarmanService
import io.vertx.howtos.ebservice.beers.impl.BarmanServiceImpl
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.serviceproxy.ServiceBinder

class BarmanVerticle : CoroutineVerticle() {

  override suspend fun start() {
    val service = BarmanServiceImpl(WebClient.create(vertx))

    ServiceBinder(vertx)
      .setAddress("beers.services.myapplication")
      .register(BarmanService::class.java, service)
  }
}
