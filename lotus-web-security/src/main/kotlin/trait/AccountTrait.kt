package io.github.llh4github.lotus.websecurity.trait

import org.springframework.security.core.userdetails.UserDetails

interface AccountTrait : UserDetails {

    fun userId(): Long

    val userIdStr get() = userId().toString()
}