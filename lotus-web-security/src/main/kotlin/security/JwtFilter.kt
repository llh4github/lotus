package io.github.llh4github.lotus.websecurity.security

import io.github.llh4github.lotus.commons.property.SecurityProperty
import io.github.llh4github.lotus.websecurity.service.JwtService
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(
    private val jwtService: JwtService,
    private val property: SecurityProperty,
) : OncePerRequestFilter() {
    private val log = KotlinLogging.logger {}

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = extractJwtToken(request)
        if (token.isNotBlank()) {
            jwtService.validateJwt(token)
        }
        filterChain.doFilter(request, response)
    }

    private fun extractJwtToken(request: HttpServletRequest): String {
        val token = request.getHeader(property.tokenHeaderName) ?: ""
        return token.removePrefix(property.tokenHeaderPrefix)
    }
}