package com.seanshubin.interceptor

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import java.net.http.HttpClient
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets

class InterceptorHandler(
    val httpClient: HttpClient,
    val restrictedHeaders: Set<String>,
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
//        val newRequestValue = requestValue.updateUri { uri ->
//            "https://members.cj.com$uri"
//        }
        val newRequestValue = requestValue.updateUri(transformer::transform)
        val httpRequest = DataTransferService.requestValueToHttpRequest(newRequestValue, restrictedHeaders)
        println(JsonMappers.pretty.writeValueAsString(newRequestValue))
        val response: HttpResponse<String> = httpClient.send(
            httpRequest,
            HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)
        )
        val responseValue = DataTransferService.httpResponseToResponseValue(response)
        println(JsonMappers.pretty.writeValueAsString(responseValue))
        DataTransferService.responseValueToExchange(responseValue, exchange)
    }
}