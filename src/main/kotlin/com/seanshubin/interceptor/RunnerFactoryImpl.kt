package com.seanshubin.interceptor

class RunnerFactoryImpl(
    private val configLoader: ConfigLoader,
    private val createRunnerWithConfig: (config: Config) -> Runnable
) : RunnerFactory {
    override fun create(): Runnable {
        val config = configLoader.loadConfig()
        return createRunnerWithConfig(config)
    }
}