package com.seanshubin.interceptor

import com.sun.net.httpserver.HttpExchange
import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse

object DataTransferService {
    fun exchangeToRequestValue(exchange: HttpExchange): RequestValue {
        return exchange.requestBody.use { requestInputStream ->
            val uri = exchange.requestURI.toString()
            val method = exchange.requestMethod
            val headers: List<Pair<String, String>> = exchange.requestHeaders.flatMap { (name, values) ->
                values.map { value ->
                    name to value
                }
            }
            val bytes = requestInputStream.readAllBytes()
            val body = if (bytes.isEmpty()) {
                null
            } else {
                String(bytes, Charsets.UTF_8)
            }
            RequestValue(uri, method, headers, body)
        }
    }

    fun requestValueToHttpRequest(requestValue: RequestValue, restrictedHeaders: Set<String>): HttpRequest {
        val builder = HttpRequest.newBuilder()
            .uri(URI.create(requestValue.uri))
            .method(requestValue.method, HttpRequest.BodyPublishers.ofString(requestValue.body ?: ""))
        requestValue.headers.forEach { (name, value) ->
            if (!restrictedHeaders.contains(name)) {
                builder.header(name, value)
            }
        }
        return builder.build()
    }

    fun responseValueToExchange(
        responseValue: ResponseValue,
        exchange: HttpExchange
    ) {
        responseValue.headers.forEach { (name, value) ->
            exchange.responseHeaders.add(name, value)
        }
        exchange.sendResponseHeaders(responseValue.statusCode, 0)
        exchange.responseBody.use { responseBody ->
            responseBody.write(responseValue.body?.toByteArray(Charsets.UTF_8) ?: ByteArray(0))
        }
    }

    fun httpResponseToResponseValue(response: HttpResponse<String>): ResponseValue {
        val statusCode = response.statusCode()
        val headers: List<Pair<String, String>> = response.headers().map().flatMap { (name, values) ->
            values.map { value ->
                name to value
            }
        }
        val responseBody = response.body()
        val body = responseBody.ifEmpty { null }
        return ResponseValue(statusCode, headers, body)
    }
}
