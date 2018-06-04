package gusev.max.cache.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import gusev.max.cache.dao.CachedUserDao
import gusev.max.cache.model.CachedUser
import javax.inject.Inject


@Database(
        entities = arrayOf(CachedUser::class),
        version = 1
)
abstract class VkPhotoDatabase @Inject constructor() : RoomDatabase() {

    abstract fun cachedUserDao(): CachedUserDao

    private var INSTANCE: VkPhotoDatabase? = null

    private val sLock = Any()

    fun getInstance(context: Context): VkPhotoDatabase {
        if (INSTANCE == null) {
            synchronized(sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            VkPhotoDatabase::class.java, "vk_debug.db"
                    )
                        .build()
                }
                return INSTANCE!!
            }
        }
        return INSTANCE!!
    }

}