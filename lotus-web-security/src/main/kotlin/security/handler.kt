package io.github.llh4github.lotus.websecurity.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.llh4github.lotus.commons.JsonWrapper
import org.springframework.http.MediaType
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler


internal fun jsonLoginFailureHandler(mapper: ObjectMapper): AuthenticationFailureHandler {
    val json = JsonWrapper<Void>(msg = "用户登录失败", code = "LOGIN_FAILED")
    return AuthenticationFailureHandler { _, response, exception ->
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        response.status = 200
        response.writer.write(mapper.writeValueAsString(json))
    }
}

internal fun jsonAccessDeniedHandler(mapper: ObjectMapper): AccessDeniedHandler {
    val json = JsonWrapper<Void>(msg = "用户无权访问", code = "ACCESS_DENIED")
    return AccessDeniedHandler { _, response, _ ->
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        response.status = 200
        response.writer.write(mapper.writeValueAsString(json))
    }
}

internal fun jsonAuthenticationEntryPoint(mapper: ObjectMapper): AuthenticationEntryPoint {
    val json = JsonWrapper<Void>(msg = "用户无权访问", code = "ACCESS_DENIED")
    return AuthenticationEntryPoint { _, response, _ ->
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        response.status = 200
        response.writer.write(mapper.writeValueAsString(json))
    }
}