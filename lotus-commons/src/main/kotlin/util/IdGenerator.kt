package io.github.llh4github.lotus.commons.util

import com.github.yitter.contract.IdGeneratorOptions
import com.github.yitter.idgen.YitIdHelper
import io.github.llh4github.lotus.commons.property.IdGeneratorProperty

open class IdGenerator(property: IdGeneratorProperty) {

    init {
        val options = IdGeneratorOptions(property.workerId)
        options.SeqBitLength = property.seqBitLength
        YitIdHelper.setIdGenerator(options)
    }


    fun nextId(): Long {
        return YitIdHelper.nextId()
    }

    fun nextIdStr(): String {
        return YitIdHelper.nextId().toString()
    }
}
