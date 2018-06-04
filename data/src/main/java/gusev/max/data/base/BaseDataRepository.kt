package gusev.max.data.base

import gusev.max.data.entity.BaseEntity
import gusev.max.data.mapper.Mapper
import gusev.max.domain.model.BaseDomainModel
import gusev.max.domain.repository.BaseRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.*


abstract class BaseDataRepository<
        E : BaseEntity,
        M : BaseDomainModel,
        DATA_STORE : BaseDataStore<E>,
        CACHE : BaseCache<E>>(
    protected var factory: BaseDataStoreFactory<E, DATA_STORE, CACHE>,
    protected var mapper: Mapper<E, M>
) : BaseRepository<M> {

    override fun clearModels(): Completable {
        return factory.retrieveCacheDataStore().clearEntities()
    }

    override fun saveModels(models: List<M>): Completable {
        return factory.retrieveCacheDataStore().saveEntities(
                models.map { mapper.mapToEntity(it) }
        )
    }

    override fun getModels(): Flowable<List<M>> {
        return factory.retrieveCacheDataStore().isCached()
            .flatMapPublisher { it -> factory.retrieveDataStore(it).getEntities() }
            .flatMap {
                Flowable.just(it.map {
                    mapper.mapFromEntity(it)
                })
            }
            .flatMap {
                saveModels(it).toSingle { it }.toFlowable()
            }
    }

    override fun getModelById(id: Long): Flowable<M> {
        return factory.retrieveCacheDataStore().isCached()
            .flatMapPublisher { it -> factory.retrieveDataStore(it).getEntityById(id) }
            .flatMap {
                Flowable.just(mapper.mapFromEntity(it))
            }
            .flatMap {
                saveModels(Collections.singletonList(it)).toSingle { it }.toFlowable()
            }
    }
}