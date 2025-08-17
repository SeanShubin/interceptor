package com.seanshubin.interceptor

class MainDependencies(val args: Array<String>) {
    val files: FilesContract = FilesDelegate
    val configLoader: ConfigLoader = ConfigLoaderImpl(files, args)
    val createRunnerWithConfig: (config: Config) -> Runnable =
        { config ->
            ConfigDependencies(config).runner
        }
    val runnerFactory: RunnerFactory = RunnerFactoryImpl(configLoader, createRunnerWithConfig)
}