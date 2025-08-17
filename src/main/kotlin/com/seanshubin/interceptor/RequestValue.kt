package com.seanshubin.interceptor

data class RequestValue(
    val uri: String,
    val method: String,
    val headers: List<Pair<String, String>>,
    val body: String?
) {
    fun updateUri(transform: (String) -> String): RequestValue = copy(uri = transform(this.uri))
}
