package gusev.max.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import gusev.max.cache.base.BaseDao
import gusev.max.cache.model.CachedUser

@Dao
abstract class CachedUserDao : BaseDao<CachedUser> {

    companion object {
        const val TABLE_NAME = "users"

        const val QUERY_USERS = "SELECT * FROM $TABLE_NAME"

        const val DELETE_USERS = "DELETE FROM $TABLE_NAME"

        const val QUERY_USER_BY_ID = "SELECT * FROM $TABLE_NAME WHERE id = :id"

    }

    @Query(QUERY_USERS)
    abstract override fun getEntities(): List<CachedUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun saveEntity(cachedEntity: CachedUser)

    @Query(DELETE_USERS)
    abstract override fun clearEntities()

    @Query(QUERY_USER_BY_ID)
    abstract override fun getEntityById(id: Long): CachedUser

}