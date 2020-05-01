package com.seanshubin.aws.login.domain

interface Http {
  fun getString(uriString: String): String
}
