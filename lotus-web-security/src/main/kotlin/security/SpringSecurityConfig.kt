package io.github.llh4github.lotus.websecurity.security

import io.github.llh4github.lotus.commons.property.SecurityProperty
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.web.SecurityFilterChain

class SpringSecurityConfig(private val property: SecurityProperty) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
        return http.build()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
//        val urls = mutableListOf("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**")
//        urls.addAll(property.annoUrl)
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring().requestMatchers(*property.annoUrl.toTypedArray())
        }
    }
}