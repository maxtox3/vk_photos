package gusev.max.vkphotos.injection

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import gusev.max.vkphotos.App
import gusev.max.vkphotos.injection.module.*
import javax.inject.Singleton



@Singleton
@Component(
        modules = [
            (ApplicationModule::class),
            (AndroidSupportInjectionModule::class),
            (CacheModule::class),
            (DataModule::class),
            (DomainModule::class),
            (PresentationModule::class),
            (RemoteModule::class),
            (UiModule::class),
            (PhotoModule::class)
        ]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: App)

}