package com.seanshubin.interceptor

class MainRunner(
    val configLoader: ConfigLoader,
    val createRunnerWithConfig: (transformationPatterns: List<Pair<String, String>>) -> Runnable
) : Runnable {
    override fun run() {
        InterceptorApp.main(emptyArray())
    }
}
