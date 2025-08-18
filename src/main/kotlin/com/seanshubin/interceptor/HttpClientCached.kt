package com.seanshubin.interceptor

import java.nio.file.Path

class HttpClientCached(
    val files: FilesContract,
    val cacheDir: Path,
    val httpClientContract: HttpClientContract
) : HttpClientContract {
    override fun send(request: RequestValue): ResponseValue {
        files.createDirectories(cacheDir)
        val file = cacheDir.resolve(request.cacheKey() + ".json")
        val cached = if (files.exists(file)) {
            loadFromFile(file)
        } else {
            val response = httpClientContract.send(request)
            val delaySeconds = 0
            val cachePayload = CachePayload(delaySeconds, request, response)
            saveToFile(file, cachePayload)
            cachePayload
        }
        if (cached.delaySeconds > 0) {
            Thread.sleep(cached.delaySeconds * 1000L)
        }
        return cached.response
    }

    private fun loadFromFile(file: Path): CachePayload {
        val json = files.readString(file)
        return JsonMappers.parse(json)
    }

    private fun saveToFile(file: Path, cachePayload: CachePayload) {
        val json = JsonMappers.pretty.writeValueAsString(cachePayload)
        files.writeString(file, json)
    }

    private fun RequestValue.cacheKey(): String {
        return "${method}-$uri".sanitize()
    }

    private fun String.sanitize(): String {
        return this.replace(Regex("[/]"), "_")
    }
}