package gusev.max.domain.interactor

import gusev.max.domain.executor.PostExecutionThread
import gusev.max.domain.executor.ThreadExecutor
import gusev.max.domain.interactor.base.CompletableUseCase
import gusev.max.domain.model.User
import gusev.max.domain.repository.UserRepository
import io.reactivex.Completable
import java.util.*
import javax.inject.Inject

class SaveUser @Inject constructor(
    private val repository: UserRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<User>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: User?): Completable {
        return repository.saveModels(Collections.singletonList(params!!))
    }
}