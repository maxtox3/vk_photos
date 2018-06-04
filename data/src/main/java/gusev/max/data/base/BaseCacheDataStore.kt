package gusev.max.data.base

import gusev.max.data.entity.BaseEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


abstract class BaseCacheDataStore<E : BaseEntity>(
    private val cache: BaseCache<E>
) : BaseDataStore<E> {

    override fun getEntities(): Flowable<List<E>> {
        return cache.getEntities()
    }

    override fun getEntityById(id: Long): Flowable<E> {
        return cache.getEntityById(id)
    }

    override fun isCached(): Single<Boolean> {
        return cache.isCached()
    }

    override fun saveEntities(entities: List<E>): Completable {
        return cache.saveEntities(entities).doOnComplete {
            cache.setLastCacheTime(System.currentTimeMillis())
        }
    }

    override fun clearEntities(): Completable {
        return cache.clearEntities()
    }
}