package gusev.max.data.base

import gusev.max.data.entity.BaseEntity


abstract class BaseDataStoreFactory<
        ENTITY_TYPE : BaseEntity,
        DATA_STORE : BaseDataStore<ENTITY_TYPE>,
        CACHE : BaseCache<ENTITY_TYPE>>(
    private val cacheStore: DATA_STORE,
    private val remoteStore: DATA_STORE,
    private val cache: CACHE
) {

    fun retrieveDataStore(isCached: Boolean?): DATA_STORE {
        return if (isCached!! && !cache.isExpired()) {
            retrieveCacheDataStore()
        } else retrieveRemoteDataStore()
    }

    fun retrieveCacheDataStore(): DATA_STORE {
        return cacheStore
    }

    fun retrieveRemoteDataStore(): DATA_STORE {
        return remoteStore
    }
}
