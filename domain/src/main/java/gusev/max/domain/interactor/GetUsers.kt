package gusev.max.domain.interactor

import gusev.max.domain.executor.PostExecutionThread
import gusev.max.domain.executor.ThreadExecutor
import gusev.max.domain.interactor.base.FlowableUseCase
import gusev.max.domain.model.User
import gusev.max.domain.repository.UserRepository
import io.reactivex.Flowable
import javax.inject.Inject


class GetUsers @Inject constructor(
    private val repository: UserRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<List<User>, Void?>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Void?): Flowable<List<User>> {
        return repository.getModels()
    }
}