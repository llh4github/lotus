package io.github.llh4github.lotus.websecurity

import io.github.llh4github.lotus.websecurity.api.AuthenticateApi
import io.github.llh4github.lotus.websecurity.service.AuthenticateService
import io.github.llh4github.lotus.websecurity.service.JwtService
import io.github.llh4github.lotus.websecurity.service.impl.AuthenticateServiceImpl
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.crypto.password.PasswordEncoder

/**
 * 业务Bean注册
 */
class BizBeanRegister {
    @Bean
    @ConditionalOnMissingBean(name = ["authenticateService"])
    fun authenticateService(
        jdbcTemplate: JdbcTemplate,
        passwordEncoder: PasswordEncoder,
        jwtService: JwtService,
    ) = AuthenticateServiceImpl(jdbcTemplate, passwordEncoder, jwtService)

    @Bean
    @ConditionalOnMissingBean(name = ["authenticateApi"])
    fun authenticateApi(authenticateService: AuthenticateService) = AuthenticateApi(authenticateService)
}