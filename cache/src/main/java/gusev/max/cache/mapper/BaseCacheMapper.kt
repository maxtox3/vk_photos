package gusev.max.cache.mapper

import gusev.max.cache.base.BaseCachedModel
import gusev.max.data.entity.BaseEntity

interface BaseCacheMapper<C: BaseCachedModel, E: BaseEntity> {

    fun mapFromCached(type: C): E

    fun mapToCached(type: E): C

}