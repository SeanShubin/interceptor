package com.seanshubin.interceptor

import java.nio.file.Path

data class Config(
    val transformationPatterns: List<Pair<String, String>>,
    val cacheDir: Path
) {
}