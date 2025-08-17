package com.seanshubin.interceptor

interface HttpClientContract {
    fun send(request: RequestValue): ResponseValue
}
