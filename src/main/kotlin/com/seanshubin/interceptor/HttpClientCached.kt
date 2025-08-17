package com.seanshubin.interceptor

import java.nio.file.Path

class HttpClientCached(
    val files: FilesContract,
    val cacheDir: Path,
    val httpClientContract: HttpClientContract
) : HttpClientContract {
    override fun send(request: RequestValue): ResponseValue {
        val file = cacheDir.resolve(request.cacheKey() + ".json")
        val response = if (files.exists(file)) {
            loadResponseFromFile(file)
        } else {
            val response = httpClientContract.send(request)
            saveResponseToFile(file, response)
            response
        }
        return response
    }

    private fun loadResponseFromFile(file: Path): ResponseValue {
        val json = files.readString(file)
        return JsonMappers.parse(json)
    }

    private fun saveResponseToFile(file: Path, response: ResponseValue) {
        val json = JsonMappers.pretty.writeValueAsString(response)
        files.writeString(file, json)
    }

    private fun RequestValue.cacheKey(): String {
        return "${method}-$uri"
    }
}