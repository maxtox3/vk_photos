package gusev.max.data.main

import gusev.max.data.base.BaseDataStoreFactory
import gusev.max.data.entity.UserEntity
import javax.inject.Inject


class UserDataStoreFactory @Inject constructor(
    cacheStore: UserCache,
    remoteDataStore: UserRemoteDataStore,
    cacheDataStore: UserCacheDataStore
) : BaseDataStoreFactory<UserEntity, UserDataStore, UserCache>(
        cache = cacheStore,
        remoteStore = remoteDataStore,
        cacheStore = cacheDataStore
)