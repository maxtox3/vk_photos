package gusev.max.vkphotos.injection.module

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import gusev.max.domain.executor.PostExecutionThread
import gusev.max.vkphotos.UiThread
import gusev.max.vkphotos.auth.AuthDialogFragment
import gusev.max.vkphotos.friends.UsersFragment
import gusev.max.vkphotos.main.MainActivity

@Module
abstract class UiModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeUsersFragment(): UsersFragment

    @ContributesAndroidInjector
    abstract fun contributeAuthDialogFragment(): AuthDialogFragment

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread
}