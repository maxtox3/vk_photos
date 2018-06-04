package gusev.max.presentation.fullphoto

import android.arch.lifecycle.ViewModel
import gusev.max.domain.executor.PostExecutionThread
import gusev.max.presentation.base.BaseViewModel
import gusev.max.presentation.base.model.TaskStatus
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


class GetPhotoViewModel @Inject constructor(
    private val processor: GetPhotoProcessor,
    private val postExecutionThread: PostExecutionThread
) : ViewModel(), BaseViewModel<GetPhotoIntent, GetPhotoViewState> {

    private var intentsSubject: PublishSubject<GetPhotoIntent> = PublishSubject.create()
    private val intentFilter: ObservableTransformer<GetPhotoIntent, GetPhotoIntent> =
        ObservableTransformer {
            it.filter({ intent -> intent !== GetPhotoIntent.InitialIntent })
        }
    private val reducer: BiFunction<GetPhotoViewState, GetPhotoResult, GetPhotoViewState> = BiFunction { previousState, result ->
        when (result) {
            is GetPhotoResult.LoadFullPhoto -> {
                when {
                    result.taskStatus == TaskStatus.SUCCESS -> GetPhotoViewState.LoadPhotoSuccess(
                            result.user
                    )
                    result.taskStatus == TaskStatus.FAILURE -> GetPhotoViewState.Failed(result.user)
                    result.taskStatus == TaskStatus.IN_WORK -> GetPhotoViewState.InProgress(result.user)
                    else -> GetPhotoViewState.Idle()
                }
            }
        }
    }
    private val statesSubject: Observable<GetPhotoViewState> = compose()

    override fun processIntents(intents: Observable<GetPhotoIntent>) {
        intents.observeOn(Schedulers.io()).subscribe(intentsSubject)
    }

    override fun states(): Observable<GetPhotoViewState> {
        return statesSubject
    }

    private fun compose(): Observable<GetPhotoViewState> {
        return intentsSubject
            .compose(intentFilter)
            .map { this.actionFromIntent(it) }
            .compose(processor.actionProcessor)
            .observeOn(postExecutionThread.scheduler)
            .scan<GetPhotoViewState>(GetPhotoViewState.Idle(), reducer)
            .replay(1)
            .autoConnect(0)
    }

    private fun actionFromIntent(intent: GetPhotoIntent): GetPhotoAction {

        return when (intent) {
            is GetPhotoIntent.InitialIntent ->
                GetPhotoAction.LoadPhoto(null)
            is GetPhotoIntent.LoadFullPhoto ->
                GetPhotoAction.LoadPhoto(intent.user)

            else -> throw UnsupportedOperationException("Oops, that looks like an unknown intent: $intent")
        }
    }
}