package io.github.llh4github.lotus.websecurity.api

import io.github.llh4github.lotus.commons.JsonWrapper
import io.github.llh4github.lotus.websecurity.service.AuthenticateService
import io.github.llh4github.lotus.websecurity.vo.LoginParam
import io.github.llh4github.lotus.websecurity.vo.LoginResultToken
import io.github.llh4github.lotus.websecurity.vo.LogoutParam
import io.github.llh4github.lotus.websecurity.vo.RefreshTokenParam
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Tag(name = "认证接口", description = "用户认证接口，登录、登出等操作")
@RequestMapping("authenticate")
class AuthenticateApi(
    private val authenticateService: AuthenticateService
) {
    @ResponseBody
    @Operation(summary = "登录接口", description = "用户登录")
    @PostMapping("login")
    fun login(@RequestBody loginParam: LoginParam): JsonWrapper<LoginResultToken> {
        val token = authenticateService.login(loginParam)
        return JsonWrapper.ok(token)
    }

    @ResponseBody
    @Operation(summary = "登出接口", description = "用户登出")
    @PostMapping("logout")
    @Parameter(name = "Authorization", description = "Bearer token", required = true, `in` = ParameterIn.HEADER)
    fun logout(@RequestBody param: LogoutParam): JsonWrapper<Boolean> {
        val logout = authenticateService.logout(param)
        return JsonWrapper.ok(logout)
    }

    @ResponseBody
    @Operation(summary = "刷新jwt", description = "禁用原有jwt，生成新的jwt")
    @PostMapping("token/refresh")
    @Parameter(name = "Authorization", description = "Bearer token", required = true, `in` = ParameterIn.HEADER)
    fun refreshToken(@RequestBody param: RefreshTokenParam): JsonWrapper<LoginResultToken> {
        val token = authenticateService.refreshToken(param)
        return JsonWrapper.ok(token)
    }
}