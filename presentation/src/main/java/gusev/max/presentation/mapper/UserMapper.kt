package gusev.max.presentation.mapper

import gusev.max.domain.model.User
import gusev.max.presentation.model.UserModel
import javax.inject.Inject


class UserMapper @Inject constructor() : Mapper<UserModel, User> {

    override fun mapToViewModel(entity: User): UserModel {
        return UserModel(
                id = entity.id,
                firstName = entity.firstName,
                lastName = entity.lastName,
                online = entity.online,
                fullPhotoId = entity.fullPhotoId,
                photo100Url = entity.photo100Url
        )
    }

    override fun mapToEntity(model: UserModel): User {
        return User(
                id = model.id,
                firstName = model.firstName,
                lastName = model.lastName,
                online = model.online,
                fullPhotoId = model.fullPhotoId,
                photo100Url = model.photo100Url
        )
    }

}