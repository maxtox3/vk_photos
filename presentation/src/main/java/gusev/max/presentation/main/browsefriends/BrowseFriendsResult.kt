package gusev.max.presentation.main.browsefriends

import gusev.max.presentation.base.BaseResult
import gusev.max.presentation.base.model.TaskStatus
import gusev.max.presentation.model.UserModel


sealed class BrowseFriendsResult : BaseResult {

    class LoadFriends(
        val taskStatus: TaskStatus,
        val users: List<UserModel>? = null,
        val error: Throwable? = null
    ) : BrowseFriendsResult() {

        companion object {

            internal fun success(userList: List<UserModel>): LoadFriends {
                return LoadFriends(
                        TaskStatus.SUCCESS,
                        userList,
                        null
                )
            }

            internal fun failure(throwable: Throwable): LoadFriends {
                return LoadFriends(
                        TaskStatus.FAILURE,
                        null,
                        throwable
                )
            }

            internal fun inFlight(): LoadFriends {
                return LoadFriends(
                        TaskStatus.IN_WORK,
                        null
                )
            }
        }
    }


}