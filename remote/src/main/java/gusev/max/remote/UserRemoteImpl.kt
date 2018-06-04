package gusev.max.remote

import gusev.max.data.entity.UserEntity
import gusev.max.data.main.UserRemote
import gusev.max.remote.api.UserApi
import gusev.max.remote.base.BaseRemoteImpl
import gusev.max.remote.mapper.UserMapper
import gusev.max.remote.model.UserModel
import gusev.max.remote.pojo.UsersPojo
import io.reactivex.Flowable
import javax.inject.Inject



class UserRemoteImpl @Inject constructor(
    private val api: UserApi,
    entityMapper: UserMapper
) : BaseRemoteImpl<UserEntity, UserModel, UsersPojo>(entityMapper),
        UserRemote {

    override fun getEntities(): Flowable<List<UserEntity>> {
        return api.getFriends()
            .compose(errorFilter)
            .map {
                it.response?.items ?: arrayListOf()
            }
            .map {
                it.map {
                    mapper.mapFromRemote(it)
                }
            }
    }

    override fun getEntityById(id: Long): Flowable<UserEntity> {
        throw UnsupportedOperationException()
    }
}