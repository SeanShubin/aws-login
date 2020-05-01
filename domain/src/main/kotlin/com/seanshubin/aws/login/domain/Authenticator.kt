package com.seanshubin.aws.login.domain

import com.seanshubin.aws.login.contract.SystemContract
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider

class Authenticator(private val commandLineArguments: Array<String>,
                    private val credentialsFactory: CredentialsFactory,
                    private val system: SystemContract,
                    private val createRunner: (AwsCredentialsProvider) -> Runnable) : Runnable {
  override fun run() {
    val profileName = commandLineArguments.getOrNull(0)
        ?: throw RuntimeException("Expected profile name as the first argument")
    val token = commandLineArguments.getOrNull(1)
    val credentialsProvider =
        if (token == null) credentialsFactory.fromCache(profileName)
        else credentialsFactory.fromToken(profileName, token)
    val runner = createRunner(credentialsProvider)
    runner.run()
  }
}
