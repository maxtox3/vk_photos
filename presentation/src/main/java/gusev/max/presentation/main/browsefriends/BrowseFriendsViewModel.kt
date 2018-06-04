package gusev.max.presentation.main.browsefriends

import android.arch.lifecycle.ViewModel
import gusev.max.presentation.base.BaseViewModel
import gusev.max.presentation.base.model.TaskStatus
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


class BrowseFriendsViewModel @Inject constructor(
    private val browseProcessor: BrowseFriendsProcessor
) : ViewModel(), BaseViewModel<BrowseFriendsIntent, BrowseFriendsViewState> {

    private var intentsSubject: PublishSubject<BrowseFriendsIntent> = PublishSubject.create()
    private val intentFilter: ObservableTransformer<BrowseFriendsIntent, BrowseFriendsIntent> =
        ObservableTransformer {
            it.filter({ intent -> intent !== BrowseFriendsIntent.InitialIntent })
        }
    private val reducer: BiFunction<BrowseFriendsViewState, BrowseFriendsResult, BrowseFriendsViewState> = BiFunction { previousState, result ->
        when (result) {
            is BrowseFriendsResult.LoadFriends -> {
                when {
                    result.taskStatus == TaskStatus.SUCCESS -> BrowseFriendsViewState.LoadFriendsSuccess(
                            result.users
                    )
                    result.taskStatus == TaskStatus.FAILURE -> BrowseFriendsViewState.Failed(
                            result.error
                    )
                    result.taskStatus == TaskStatus.IN_WORK -> BrowseFriendsViewState.InProgress
                    else -> BrowseFriendsViewState.Idle()
                }
            }
        }
    }
    private val statesSubject: Observable<BrowseFriendsViewState> = compose()

    override fun processIntents(intents: Observable<BrowseFriendsIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): Observable<BrowseFriendsViewState> {
        return statesSubject
    }

    private fun compose(): Observable<BrowseFriendsViewState> {
        return intentsSubject
            .compose(intentFilter)
            .map { this.actionFromIntent(it) }
            .compose(browseProcessor.actionProcessor)
            .scan<BrowseFriendsViewState>(BrowseFriendsViewState.Idle(), reducer)
            .replay(1)
            .autoConnect(0)
    }

    private fun actionFromIntent(intent: BrowseFriendsIntent): BrowseFriendsAction {

        return when (intent) {
            is BrowseFriendsIntent.BrowseFriends ->
                BrowseFriendsAction.LoadFriends
            is BrowseFriendsIntent.TryAgain ->
                BrowseFriendsAction.LoadFriends
            is BrowseFriendsIntent.InitialIntent ->
                BrowseFriendsAction.LoadFriends

            else -> throw UnsupportedOperationException("Oops, that looks like an unknown intent: $intent")
        }
    }
}