package com.seanshubin.interceptor

import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress
import java.net.http.HttpClient

class ConfigDependencies(
    config: Config
) {
    val restrictedHeaders = setOf("Connection", "Host", "Content-length")
    val port = 8080
    val maxQueuedConnections = 0
    val files: FilesContract = FilesDelegate
    val address = InetSocketAddress(port)
    val httpServer: HttpServer = HttpServer.create(address, maxQueuedConnections)
    val javaHttpClient: HttpClient = HttpClient.newHttpClient()
    val httpClientValues: HttpClientContract = HttpClientDelegate(javaHttpClient, restrictedHeaders)
    val httpClientCached: HttpClientContract = HttpClientCached(
        files,
        config.cacheDir,
        httpClientValues)
    val transformer: Transformer = TransformerImpl(config.transformationPatterns)
    val httpHandler: HttpHandler = InterceptorHandler(httpClientCached, transformer)
    val runner: Runnable = Interceptor(httpServer, httpHandler)
}