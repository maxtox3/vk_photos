package gusev.max.data.mapper

import gusev.max.data.entity.UserEntity
import gusev.max.domain.model.User
import javax.inject.Inject


class UserMapper @Inject constructor() : Mapper<UserEntity, User> {

    override fun mapFromEntity(type: UserEntity): User {
        return User(
                id = type.id,
                firstName = type.firstName,
                lastName = type.lastName,
                online = type.online,
                fullPhotoId = type.fullPhotoId,
                photo100Url = type.photo100Url
        )
    }

    override fun mapToEntity(type: User): UserEntity {
        return UserEntity(
                id = type.id,
                firstName = type.firstName,
                lastName = type.lastName,
                online = type.online,
                fullPhotoId = type.fullPhotoId,
                photo100Url = type.photo100Url
        )
    }

}