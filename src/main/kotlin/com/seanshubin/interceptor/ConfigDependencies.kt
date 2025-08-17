package com.seanshubin.interceptor

import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress
import java.net.http.HttpClient

class ConfigDependencies(private val transformationPatterns: List<Pair<String, String>>) {
    val restrictedHeaders = setOf("Connection", "Host")
    val port = 8080
    val maxQueuedConnections = 0
    val address = InetSocketAddress(port)
    val httpServer: HttpServer = HttpServer.create(address, maxQueuedConnections)
    val javaHttpClient: HttpClient = HttpClient.newHttpClient()
    val valueHttpClient: HttpClientContract = HttpClientDelegate(javaHttpClient, restrictedHeaders)
    val transformer: Transformer = TransformerImpl(transformationPatterns)
    val httpHandler: HttpHandler = InterceptorHandler(valueHttpClient, restrictedHeaders, transformer)
    val runner: Runnable = Interceptor(httpServer, httpHandler)
}