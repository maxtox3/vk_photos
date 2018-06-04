package gusev.max.cache

import gusev.max.cache.base.BaseCacheImpl
import gusev.max.cache.base.BaseDao
import gusev.max.cache.db.VkPhotoDatabase
import gusev.max.cache.mapper.UserMapper
import gusev.max.cache.model.CachedUser
import gusev.max.data.entity.UserEntity
import gusev.max.data.main.UserCache
import javax.inject.Inject

class UserCacheImpl @Inject constructor(
    private val database: VkPhotoDatabase,
    mapper: UserMapper,
    private val preferencesHelper: PreferencesHelper
) : BaseCacheImpl<UserEntity, CachedUser>(mapper), UserCache {

    override fun setLastCacheTime(lastCacheTime: Long) {
        preferencesHelper.lastUsersChangeTime = lastCacheTime
    }

    override fun getDao(): BaseDao<CachedUser> {
        return database.cachedUserDao()
    }

    override fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.lastUsersChangeTime
    }

}