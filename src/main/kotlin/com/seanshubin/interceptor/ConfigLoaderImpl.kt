package com.seanshubin.interceptor

import java.nio.file.Path

class ConfigLoaderImpl(
    private val files: FilesContract,
    private val args: Array<String>
) : ConfigLoader {
    override fun loadReplacePatterns(): List<Pair<String, String>> {
        val configFile = Path.of(args[0])
        val json = files.readString(configFile)
        val config = JsonMappers.parse<Any>(json)
        config as List<*>
        val transformationPatterns = config.map { it as Map<*, *> }
            .map { Pair(it["pattern"] as String, it["replace"] as String) }
        return transformationPatterns
    }
}