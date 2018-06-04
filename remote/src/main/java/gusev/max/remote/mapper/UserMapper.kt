package gusev.max.remote.mapper

import gusev.max.data.entity.UserEntity
import gusev.max.remote.model.UserModel
import javax.inject.Inject


class UserMapper @Inject constructor() : BaseRemoteMapper<UserModel, UserEntity> {

    override fun mapFromRemote(type: UserModel): UserEntity {
        return UserEntity(
                id = type.id,
                firstName = type.firstName ?: "",
                lastName = type.lastName ?: "",
                online = type.online == 1,
                fullPhotoId = type.photoId ?: "",
                photo100Url = type.photo100Url ?: ""
        )
    }

}