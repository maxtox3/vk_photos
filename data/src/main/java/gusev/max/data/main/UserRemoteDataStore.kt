package gusev.max.data.main

import gusev.max.data.base.BaseRemoteDataStore
import gusev.max.data.entity.UserEntity
import javax.inject.Inject


class UserRemoteDataStore @Inject constructor(
    remote: UserRemote
) : BaseRemoteDataStore<UserEntity>(remote), UserDataStore