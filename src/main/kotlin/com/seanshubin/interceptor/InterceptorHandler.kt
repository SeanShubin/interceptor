package com.seanshubin.interceptor

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler

class InterceptorHandler(
    val httpClient: HttpClientContract,
    val transformer: Transformer
) : HttpHandler {
    override fun handle(exchange: HttpExchange) {
        try {
            handleCouldThrow(exchange)
        } catch (ex: Throwable) {
            ex.printStackTrace()
        }
    }

    private fun handleCouldThrow(exchange: HttpExchange) {
        val requestValue = DataTransferService.exchangeToRequestValue(exchange)
        val newRequestValue = requestValue.updateUri(transformer::transform)
        println("${requestValue.uri} -> ${newRequestValue.uri}")
        val responseValue = httpClient.send(newRequestValue)
        DataTransferService.responseValueToExchange(responseValue, exchange)
    }
}