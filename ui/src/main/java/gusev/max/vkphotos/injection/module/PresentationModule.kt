package gusev.max.vkphotos.injection.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import gusev.max.presentation.auth.AuthViewModel
import gusev.max.presentation.fullphoto.GetPhotoViewModel
import gusev.max.presentation.main.browsefriends.BrowseFriendsViewModel
import gusev.max.presentation.mapper.AuthDataMapper
import gusev.max.vkphotos.injection.ViewModelFactory
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class PresentationModule {

    @Binds
    @IntoMap
    @ViewModelKey(BrowseFriendsViewModel::class)
    abstract fun bindBrowseFriendsViewModel(viewModel: BrowseFriendsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GetPhotoViewModel::class)
    abstract fun bindGetPhotoViewModel(viewModel: GetPhotoViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideAuthDataMapper(): AuthDataMapper {
            return AuthDataMapper
        }
    }
}