package io.github.llh4github.lotus.websecurity

import io.github.llh4github.lotus.commons.property.SecurityProperty
import io.github.llh4github.lotus.commons.util.IdGenerator
import io.github.llh4github.lotus.websecurity.security.SpringSecurityConfig
import io.github.llh4github.lotus.websecurity.service.impl.JwtServiceImpl
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class BeanRegister {

    @Bean
    @ConfigurationProperties(prefix = "lotus.web.security")
    fun securityProperty(): SecurityProperty {
        return SecurityProperty()
    }

    @Bean
    fun springSecurityConfig(property: SecurityProperty) = SpringSecurityConfig(property)

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    @ConditionalOnMissingBean(name = ["jwtService"])
    fun jwtService(
        property: SecurityProperty,
        idGenerator: IdGenerator,
        redisTemplate: StringRedisTemplate,
    ) = JwtServiceImpl(property, idGenerator, redisTemplate)
}