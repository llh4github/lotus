package io.github.llh4github.lotus.websecurity.service.impl

import io.github.llh4github.lotus.commons.property.SecurityProperty
import io.github.llh4github.lotus.commons.util.IdGenerator
import io.github.llh4github.lotus.websecurity.consts.JwtTokenType
import io.github.llh4github.lotus.websecurity.service.JwtService
import io.github.llh4github.lotus.websecurity.trait.AccountTrait
import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.util.DigestUtils
import java.util.*

class JwtServiceImpl(
    private val property: SecurityProperty,
    private val idGenerator: IdGenerator,
    private val redisTemplate: StringRedisTemplate,
) : JwtService {
    //#region 类属性
    private val logger = KotlinLogging.logger {}
    private val secretKey by lazy {
        val bytes = Decoders.BASE64.decode(property.secret)
        Keys.hmacShaKeyFor(bytes)
    }
    private val parser by lazy {
        Jwts.parser().verifyWith(secretKey).build()
    }
    private val keyPrefix = property.cacheKeyPrefix
    //#endregion

    override fun createToken(trait: AccountTrait, type: JwtTokenType): String {
        val expire = if (type == JwtTokenType.ACCESS_TOKEN) {
            property.tokenExpireTime.accessExpireTime
        } else {
            property.tokenExpireTime.refreshExpireTime
        }

        val map = mapOf<String, Any>(
            "username" to trait.username,
            "userId" to trait.userId(),
        )
        val tokenId = idGenerator.nextIdStr()
        val builder = Jwts.builder()
            .id(tokenId)
            .claims(map)
            .signWith(secretKey)
            .expiration(expire)

        builder.header().add("typ", type.name)
        val token = builder.compact()
        cacheToken(token, expire)
        return token
    }

    override fun isValid(token: String): Boolean {
        try {
            parser.parse(token).payload
            return isCached(token)
        } catch (e: Exception) {
            logger.warn(e) { "token验证出错: $token" }
            return false
        }
    }


    //#region token缓存操作

    fun cacheToken(token: String, expire: Date) {
        val hash = DigestUtils.md5Digest(token.toByteArray())
        val key = keyPrefix + "jwt:$hash"
        redisTemplate.opsForValue().set(key, token)
        redisTemplate.expireAt(key, expire)
    }

    fun isCached(token: String): Boolean {
        val hash = DigestUtils.md5Digest(token.toByteArray())
        val key = keyPrefix + "jwt:$hash"
        return redisTemplate.hasKey(key)
    }

    override fun removeToken(token: String): Boolean {
        val hash = DigestUtils.md5Digest(token.toByteArray())
        val key = keyPrefix + "jwt:$hash"
        return redisTemplate.delete(key)
    }
    //#endregion

}