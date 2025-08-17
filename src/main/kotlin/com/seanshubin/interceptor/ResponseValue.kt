package com.seanshubin.interceptor

data class ResponseValue(
    val statusCode: Int,
    val headers: List<Pair<String, String>>,
    val body: String?
)