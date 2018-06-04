package gusev.max.data

import gusev.max.data.base.BaseDataRepository
import gusev.max.data.entity.UserEntity
import gusev.max.data.main.UserCache
import gusev.max.data.main.UserDataStore
import gusev.max.data.main.UserDataStoreFactory
import gusev.max.data.mapper.UserMapper
import gusev.max.domain.model.User
import gusev.max.domain.repository.UserRepository
import javax.inject.Inject


class UsersDataRepository @Inject constructor(
    dataStoreFactory: UserDataStoreFactory,
    entityMapper: UserMapper
) : BaseDataRepository<UserEntity, User, UserDataStore, UserCache>(dataStoreFactory, entityMapper),
        UserRepository