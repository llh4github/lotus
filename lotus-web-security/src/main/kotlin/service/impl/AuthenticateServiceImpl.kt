package io.github.llh4github.lotus.websecurity.service.impl

import io.github.llh4github.lotus.websecurity.service.AuthenticateService
import io.github.llh4github.lotus.websecurity.service.JwtService
import io.github.llh4github.lotus.websecurity.vo.*
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import java.sql.ResultSet

class AuthenticateServiceImpl(
    private val jdbcTemplate: JdbcTemplate,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
) : AuthenticateService {
    private val logger = KotlinLogging.logger {}

    override fun login(param: LoginParam): LoginResultToken {
        findByUsername(param.username)?.let {
            val matched = passwordEncoder.matches(param.password, it.password)
            if (matched) {
                val access = jwtService.createAccessToken(it)
                val refresh = jwtService.createRefreshToken(it)
                return LoginResultToken(
                    username = it.username,
                    accessToken = access,
                    refreshToken = refresh
                )
            }
            logger.warn { "$param 密码不匹配" }
        }

        logger.warn { "用户名${param.username}未找到对应用户" }
        throw BadCredentialsException("用户名或密码错误")
    }

    override fun logout(param: LogoutParam): Boolean {
        jwtService.removeToken(param.accessToken)
        jwtService.removeToken(param.refreshToken)
        SecurityContextHolder.clearContext()
        return true
    }

    override fun refreshToken(param: RefreshTokenParam): LoginResultToken {
        jwtService.removeToken(param.accessToken)
        jwtService.removeToken(param.refreshToken)

        findByUsername(param.username)?.let {
            val access = jwtService.createAccessToken(it)
            val refresh = jwtService.createRefreshToken(it)
            return LoginResultToken(
                username = it.username,
                accessToken = access,
                refreshToken = refresh
            )
        }
        throw BadCredentialsException("用户名或密码错误")
    }

    private fun findByUsername(username: String): DefaultUser? {
        val sql = "select id,username,password from auth_user where username = ?"
        logger.debug { "findByUsername: $sql" }
        return jdbcTemplate.queryForObject(sql, DefaultUserMapper(), username)
    }
}

internal class DefaultUserMapper : RowMapper<DefaultUser> {
    override fun mapRow(rs: ResultSet, rowNum: Int): DefaultUser? {
        return try {
            DefaultUser(
                id = rs.getLong("id"),
                uname = rs.getString("username"),
                pwd = rs.getString("password")
            )
        } catch (e: Exception) {
            null
        }
    }
}