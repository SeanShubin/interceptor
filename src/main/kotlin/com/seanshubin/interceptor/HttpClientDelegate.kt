package com.seanshubin.interceptor

import java.net.http.HttpClient
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets

class HttpClientDelegate(
    private val httpClient: HttpClient,
    private val restrictedHeaders: Set<String>
) : HttpClientContract {
    override fun send(request: RequestValue): ResponseValue {
        val httpRequest = DataTransferService.requestValueToHttpRequest(request, restrictedHeaders)
        val httpResponse: HttpResponse<String> = httpClient.send(
            httpRequest,
            HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)
        )
        val response = DataTransferService.httpResponseToResponseValue(httpResponse)
        return response
    }
}