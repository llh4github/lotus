package io.github.llh4github.lotus.webcore.dal

import org.babyfish.jimmer.sql.MappedSuperclass

@MappedSuperclass
interface LogicDeleteModel : BaseModel, LogicDeleteAware {
}