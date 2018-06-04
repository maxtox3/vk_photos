package gusev.max.vkphotos.injection.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import gusev.max.data.auth.AuthCache
import gusev.max.data.main.UserRemote
import gusev.max.remote.UserRemoteImpl
import gusev.max.remote.VkService
import gusev.max.remote.api.UserApi
import gusev.max.vkontakte_photos.BuildConfig

@Module
abstract class RemoteModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideUserApi(cache: AuthCache): UserApi {
            return VkService.makeUserApi(
                    BuildConfig.DEBUG,
                    BuildConfig.VK_API_URL,
                    cache
            )
        }
    }

    @Binds
    abstract fun bindUserRemote(userRemoteImpl: UserRemoteImpl): UserRemote
}