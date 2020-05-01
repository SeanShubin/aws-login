package com.seanshubin.aws.login.domain

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider

interface CredentialsFactory {
  fun fromToken(profileName: String, token: String): AwsCredentialsProvider
  fun fromCache(profileName: String): AwsCredentialsProvider
}
