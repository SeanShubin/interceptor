package com.seanshubin.interceptor

object InterceptorApp {
    @JvmStatic
    fun main(args: Array<String>) {
        MainDependencies(args).runnerFactory.create().run()
    }
}
