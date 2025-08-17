package com.seanshubin.interceptor

interface ConfigLoader {
    fun loadReplacePatterns(): List<Pair<String, String>>
}
