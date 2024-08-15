package io.github.llh4github.lotus.webcore

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.meta.DatabaseNamingStrategy
import org.babyfish.jimmer.sql.runtime.DefaultDatabaseNamingStrategy
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.annotation.Bean

/**
 * 数据库访问层配置
 */
@ConditionalOnBean(value = [KSqlClient::class])
open class DaoLayerConfig {

    @Bean
    fun databaseNamingStrategy(): DatabaseNamingStrategy =
        DefaultDatabaseNamingStrategy.LOWER_CASE

}