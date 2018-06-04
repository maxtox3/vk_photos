package gusev.max.remote.base

import gusev.max.data.base.BaseRemote
import gusev.max.data.entity.BaseEntity
import gusev.max.domain.error.AuthError
import gusev.max.remote.mapper.BaseRemoteMapper
import gusev.max.remote.model.BaseRemoteModel
import gusev.max.remote.pojo.BasePojo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Single


abstract class BaseRemoteImpl<E : BaseEntity, M : BaseRemoteModel, P : BasePojo<M>>(
    val mapper: BaseRemoteMapper<M, E>
) : BaseRemote<E> {

    val errorFilter: FlowableTransformer<P, P> = FlowableTransformer {
        it.switchMap {
            if (it.error?.errorCode != null) {
                Flowable.error(AuthError())
            } else {
                Flowable.just(it)
            }
        }
    }

    override fun isCached(): Single<Boolean> {
        throw UnsupportedOperationException()
    }

    override fun saveEntities(entities: List<E>): Completable {
        throw UnsupportedOperationException()
    }

    override fun clearEntities(): Completable {
        throw UnsupportedOperationException()
    }
}