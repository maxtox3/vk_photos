package gusev.max.vkphotos.injection.module

import android.app.Application
import android.arch.persistence.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import gusev.max.cache.AuthCacheImpl
import gusev.max.cache.UserCacheImpl
import gusev.max.cache.db.VkPhotoDatabase
import gusev.max.data.auth.AuthCache
import gusev.max.data.main.UserCache

@Module
abstract class CacheModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideVkPhotosDatabase(application: Application): VkPhotoDatabase {
            return Room.databaseBuilder(
                    application.applicationContext,
                    VkPhotoDatabase::class.java,
                    "vk_photos.db"
            ).build()
        }
    }

    @Binds
    abstract fun bindUserCache(userCacheImpl: UserCacheImpl): UserCache

    @Binds
    abstract fun bindAuthCache(authCacheImpl: AuthCacheImpl): AuthCache

}