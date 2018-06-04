package gusev.max.vkphotos.injection.module

import dagger.Binds
import dagger.Module
import gusev.max.data.AuthDataRepository
import gusev.max.data.UsersDataRepository
import gusev.max.data.executor.JobExecutor
import gusev.max.domain.executor.ThreadExecutor
import gusev.max.domain.repository.AuthRepository
import gusev.max.domain.repository.UserRepository

@Module
abstract class DataModule {

    @Binds
    abstract fun bindUserRepository(usersDataRepository: UsersDataRepository): UserRepository

    @Binds
    abstract fun bindAuthRepository(authDataRepository: AuthDataRepository): AuthRepository

    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor
}