package com.seanshubin.interceptor

data class CachePayload(
    val delaySeconds: Int,
    val request: RequestValue,
    val response: ResponseValue
) {
}
