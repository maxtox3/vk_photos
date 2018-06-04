package gusev.max.domain.interactor

import gusev.max.domain.executor.PostExecutionThread
import gusev.max.domain.executor.ThreadExecutor
import gusev.max.domain.interactor.base.CompletableUseCase
import gusev.max.domain.model.AuthData
import gusev.max.domain.repository.AuthRepository
import io.reactivex.Completable
import javax.inject.Inject


class SaveAuthUserData @Inject constructor(
    private val repository: AuthRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<AuthData>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: AuthData?): Completable {
        return repository.saveAuthData(params!!)
    }


}