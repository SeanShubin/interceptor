package com.seanshubin.interceptor

import java.nio.file.Path
import java.nio.file.Paths

class ConfigLoaderImpl(
    private val files: FilesContract,
    private val args: Array<String>
) : ConfigLoader {
    override fun loadConfig(): Config {
        val configFile = Path.of(args[0])
        val json = files.readString(configFile)
        val config = JsonMappers.parse<Any>(json) as Map<String, *>
        val transformationPatternsObject = config.getValue("transformationPatterns") as List<*>
        val transformationPatterns = transformationPatternsObject.map { it as Map<*, *> }
            .map { Pair(it["pattern"] as String, it["replace"] as String) }
        val cacheDirName = config.getValue("cacheDir") as String
        val cacheDir = Paths.get(cacheDirName)
        return Config(transformationPatterns, cacheDir)
    }
}