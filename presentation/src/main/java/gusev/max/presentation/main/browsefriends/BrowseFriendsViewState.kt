package gusev.max.presentation.main.browsefriends

import gusev.max.presentation.base.BaseViewState
import gusev.max.presentation.model.UserModel


sealed class BrowseFriendsViewState(
    val inProgress: Boolean = false,
    val users: List<UserModel>? = null,
    val throwable: Throwable? = null
) : BaseViewState {

    object InProgress : BrowseFriendsViewState(true, null)

    class Failed(error: Throwable?) : BrowseFriendsViewState(false, null, error)

    class LoadFriendsSuccess(userList: List<UserModel>?) :
            BrowseFriendsViewState(false, userList)

    class Idle : BrowseFriendsViewState(false, null)
}