package com.seanshubin.interceptor

import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer

class Interceptor(
    val httpServer: HttpServer,
    val httpHandler: HttpHandler,
) : Runnable {
    override fun run() {
        httpServer.createContext("/", httpHandler)
        httpServer.start()
    }
}