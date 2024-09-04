package io.github.llh4github.lotus.websecurity.vo

import io.github.llh4github.lotus.websecurity.trait.AccountTrait
import org.springframework.security.core.GrantedAuthority

data class DefaultUser(
    val id: Long,
    private val uname: String,
    private val pwd: String,
) : AccountTrait {
    override fun userId(): Long {
        return id
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun getPassword(): String {
        return pwd
    }

    override fun getUsername(): String {
        return uname
    }
}