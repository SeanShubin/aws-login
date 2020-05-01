package com.seanshubin.aws.login.console

object EntryPoint {
  @JvmStatic
  fun main(args: Array<String>) {
    DependencyInjectionCommandLineArguments(args).runner.run()
  }
}