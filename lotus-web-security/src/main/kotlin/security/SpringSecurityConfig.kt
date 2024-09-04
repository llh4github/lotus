package io.github.llh4github.lotus.websecurity.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.llh4github.lotus.commons.property.SecurityProperty
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class SpringSecurityConfig(
    private val property: SecurityProperty,
    private val objectMapper: ObjectMapper,
    private val jwtFilter: JwtFilter,
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .logout { it.disable() }
            .exceptionHandling {
                it.accessDeniedHandler(jsonAccessDeniedHandler(objectMapper))
                it.authenticationEntryPoint(jsonAuthenticationEntryPoint(objectMapper))
            }.sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
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