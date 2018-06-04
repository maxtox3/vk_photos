package gusev.max.data.main

import gusev.max.data.base.BaseCacheDataStore
import gusev.max.data.entity.UserEntity
import javax.inject.Inject


class UserCacheDataStore @Inject constructor(
    cache: UserCache
) : BaseCacheDataStore<UserEntity>(cache), UserDataStore