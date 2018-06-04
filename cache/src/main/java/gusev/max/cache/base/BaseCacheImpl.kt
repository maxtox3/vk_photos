package gusev.max.cache.base

import gusev.max.cache.mapper.BaseCacheMapper
import gusev.max.data.base.BaseCache
import gusev.max.data.entity.BaseEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

abstract class BaseCacheImpl<ENTITY : BaseEntity, CACHED_ENTITY : BaseCachedModel>(
    private val entityMapper: BaseCacheMapper<CACHED_ENTITY, ENTITY>
) : BaseCache<ENTITY> {

    private val EXPIRATION_TIME = (60 * 10 * 1000).toLong()


    override fun getEntities(): Flowable<List<ENTITY>> {
        return Flowable.defer {
            Flowable.just(getDao().getEntities())
        }.map {
            it.map { entityMapper.mapFromCached(it) }
        }
    }

    override fun getEntityById(id: Long): Flowable<ENTITY> {
        return Flowable.defer {
            Flowable.just(getDao().getEntityById(id))
        }.map {
            entityMapper.mapFromCached(it)
        }
    }

    override fun isCached(): Single<Boolean> {
        return Single.defer {
            Single.just(getDao().getEntities().isNotEmpty())
        }
    }

    override fun saveEntities(entities: List<ENTITY>): Completable {
        return Completable.defer {
            entities.forEach {
                getDao().saveEntity(entityMapper.mapToCached(it))
            }
            Completable.complete()
        }
    }

    override fun clearEntities(): Completable {
        return Completable.defer {
            getDao().clearEntities()
            Completable.complete()
        }
    }

    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    abstract fun getDao(): BaseDao<CACHED_ENTITY>

    protected abstract fun getLastCacheUpdateTimeMillis(): Long

}