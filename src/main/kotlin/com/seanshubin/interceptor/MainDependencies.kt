package com.seanshubin.interceptor

class MainDependencies(val args: Array<String>) {
    val files: FilesContract = FilesDelegate
    val configLoader: ConfigLoader = ConfigLoaderImpl(files, args)
    val createRunnerWithConfig: (transformationPatterns: List<Pair<String, String>>) -> Runnable =
        { transformationPatterns ->
            ConfigDependencies(transformationPatterns).runner
        }
    val runnerFactory: RunnerFactory = RunnerFactoryImpl(configLoader, createRunnerWithConfig)
}