package io.github.llh4github.lotus.webcore.dal

import io.github.llh4github.lotus.commons.PageQueryParamTrait
import io.github.llh4github.lotus.commons.PageResult
import org.babyfish.jimmer.sql.kt.ast.query.KConfigurableRootQuery
import java.sql.Connection


/**
 * 自定义分页方法
 */
public fun <E> KConfigurableRootQuery<*, E>.fetchPage(
    pageParams: PageQueryParamTrait,
    con: Connection? = null
): PageResult<E> {
    return this.fetchPage(pageParams.pageNum(), pageParams.pageSize, con) { entities, totalCount, _ ->
        PageResult(
            totalCount,
            (totalCount + pageParams.pageNum()) / pageParams.pageSize,
            entities,
        )
    }
}
