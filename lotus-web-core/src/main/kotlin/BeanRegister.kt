package io.github.llh4github.lotus.webcore

import io.github.llh4github.lotus.commons.property.IdGeneratorProperty
import io.github.llh4github.lotus.commons.util.IdGenerator
import io.github.llh4github.lotus.webcore.dal.TableIdGenerator
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.annotation.Bean
import javax.sql.DataSource

open class BeanRegister {

    @Bean
    @ConditionalOnBean(value = [IdGeneratorProperty::class])
    fun idGenerator(property: IdGeneratorProperty) = IdGenerator(property)

    @Bean
    @ConditionalOnBean(value = [IdGenerator::class, DataSource::class])
    fun tableIdGenerator(idGenerator: IdGenerator) = TableIdGenerator(idGenerator)
}