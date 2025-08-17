package com.seanshubin.interceptor

class RunnerFactoryImpl(
    private val configLoader: ConfigLoader,
    private val createRunnerWithConfig: (transformationPatterns: List<Pair<String, String>>) -> Runnable
) : RunnerFactory {
    override fun create(): Runnable {
        val transformationPatterns = configLoader.loadReplacePatterns()
        return createRunnerWithConfig(transformationPatterns)
    }
}