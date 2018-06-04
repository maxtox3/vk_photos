package gusev.max.presentation.main.browsefriends

import gusev.max.domain.interactor.GetUsers
import gusev.max.presentation.mapper.UserMapper
import io.reactivex.ObservableTransformer
import javax.inject.Inject


class BrowseFriendsProcessor @Inject constructor(
    private val getUsers: GetUsers,
    private val mapper: UserMapper
) {

    private val processor: ObservableTransformer<BrowseFriendsAction, BrowseFriendsResult> = ObservableTransformer {
        it.switchMap {
            getUsers.execute()
                .map {
                    BrowseFriendsResult.LoadFriends.success(
                            it.map {
                                mapper.mapToViewModel(it)
                            })
                }
                .onErrorReturn {
                    BrowseFriendsResult.LoadFriends.failure(
                            it
                    )
                }
                .toObservable()
                .startWith(BrowseFriendsResult.LoadFriends.inFlight())
        }
    }

    var actionProcessor: ObservableTransformer<BrowseFriendsAction, BrowseFriendsResult>

    init {
        this.actionProcessor = ObservableTransformer {
            it.publish {
                it.ofType(BrowseFriendsAction::class.java)
                    .compose(processor)
            }
        }
    }
}