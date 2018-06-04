package gusev.max.cache.base

interface BaseDao<CACHED_ENTITY : BaseCachedModel> {

    fun getEntities(): List<CACHED_ENTITY>

    fun getEntityById(id: Long): CACHED_ENTITY

    fun saveEntity(cachedEntity: CACHED_ENTITY)

    fun clearEntities()
}