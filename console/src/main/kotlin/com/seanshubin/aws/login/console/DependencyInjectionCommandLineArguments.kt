package com.seanshubin.aws.login.console

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.seanshubin.aws.login.contract.FilesContract
import com.seanshubin.aws.login.contract.FilesDelegate
import com.seanshubin.aws.login.contract.SystemContract
import com.seanshubin.aws.login.contract.SystemDelegate
import com.seanshubin.aws.login.domain.*
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.services.sts.StsClient
import java.net.http.HttpClient
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.nio.file.Paths

class DependencyInjectionCommandLineArguments(args: Array<String>) {
  private val objectMapper: ObjectMapper = ObjectMapper()
      .registerKotlinModule()
      .configure(SerializationFeature.INDENT_OUTPUT, true)
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  private val cacheDir: Path = Paths.get("out/cache")
  private val profileDir: Path = Paths.get("local-config/profile")
  private val files: FilesContract = FilesDelegate
  private val stsClient: StsClient = StsClient.builder().build()
  private val charset: Charset = StandardCharsets.UTF_8
  private val httpClient: HttpClient = HttpClient.newHttpClient()
  private val http: Http = JavaHttp(httpClient)
  private val credentialsFactory: CredentialsFactory = CredentialsFactoryImpl(
      objectMapper,
      cacheDir,
      profileDir,
      files,
      stsClient,
      charset,
      http)
  private val system: SystemContract = SystemDelegate
  private val createRunner: (AwsCredentialsProvider) -> Runnable = { credentialsProvider ->
    DependencyInjectionCredentialsProvider(credentialsProvider).runner
  }
  val runner: Runnable = Authenticator(args, credentialsFactory, system, createRunner)
}
