package io.github.llh4github.lotus.commons.property

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import kotlin.time.Duration
import kotlin.time.toJavaDuration

class SecurityProperty {

    /**
     * 签发人。通常是访问域名。
     */
    var issuer: String = "lotus-web-security"

    /**
     * 令牌秘钥
     *
     * 用base64，至少需要43个字符，不含特殊符号。
     */
    var secret: String = "VyHZ8YGV9w94dRw8ixVzJgcoDXqvRGrFOHzCxiMIgbgmM"

    /**
     * 缓存key前缀
     */
    var cacheKeyPrefix: String = "lotus-web-security"

    /**
     * 令牌过期时间
     */
    var tokenExpireTime: TokenExpireTime = TokenExpireTime()

    /**
     * 允许匿名访问的url列表
     */
    var annoUrl: List<String> = emptyList()
}

data class TokenExpireTime(
    var access: Duration = Duration.parse("1d"),
    var refresh: Duration = Duration.parse("7d"),
) {
    val accessExpireTime: Date
        get() {
            val instant = LocalDateTime.now()
                .plus(access.toJavaDuration())
                .atZone(ZoneId.systemDefault()).toInstant()
            return Date.from(instant)
        }


    val refreshExpireTime: Date
        get() {
            val instant = LocalDateTime.now()
                .plus(refresh.toJavaDuration())
                .atZone(ZoneId.systemDefault()).toInstant()
            return Date.from(instant)
        }
}