package com.seanshubin.aws.login.console

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider

class DependencyInjectionCredentialsProvider(
    private val credentialsProvider: AwsCredentialsProvider) {
  val runner: Runnable = Runnable { }
}
