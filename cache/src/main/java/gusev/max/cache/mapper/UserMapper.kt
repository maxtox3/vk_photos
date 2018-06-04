package gusev.max.cache.mapper

import gusev.max.cache.model.CachedUser
import gusev.max.data.entity.UserEntity
import javax.inject.Inject

class UserMapper @Inject constructor() : BaseCacheMapper<CachedUser, UserEntity> {
    override fun mapFromCached(type: CachedUser): UserEntity {
        return UserEntity(
                id = type.id,
                firstName = type.firstName,
                lastName = type.lastName,
                online = type.online,
                fullPhotoId = type.fullPhotoId,
                photo100Url = type.photo100Url
                )
    }

    override fun mapToCached(type: UserEntity): CachedUser {
        return CachedUser(
                id = type.id,
                firstName = type.firstName,
                lastName = type.lastName,
                online = type.online,
                fullPhotoId = type.fullPhotoId,
                photo100Url = type.photo100Url
        )
    }


}