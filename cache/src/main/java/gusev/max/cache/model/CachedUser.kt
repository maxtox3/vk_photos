package gusev.max.cache.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import gusev.max.cache.base.BaseCachedModel
import gusev.max.cache.dao.CachedUserDao

@Entity(tableName = CachedUserDao.TABLE_NAME)
data class CachedUser(
    @PrimaryKey
    val id: Long,
    val firstName: String,
    val lastName: String,
    val online: Boolean,
    val fullPhotoId: String,
    val photo100Url: String
) : BaseCachedModel