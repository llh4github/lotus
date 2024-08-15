package io.github.llh4github.lotus.webcore.dal

import io.github.llh4github.lotus.commons.util.IdGenerator
import org.babyfish.jimmer.sql.meta.UserIdGenerator

class TableIdGenerator(private val idGenerator: IdGenerator) : UserIdGenerator<Long> {
    override fun generate(entityType: Class<*>?): Long {
        return idGenerator.nextId()
    }
}