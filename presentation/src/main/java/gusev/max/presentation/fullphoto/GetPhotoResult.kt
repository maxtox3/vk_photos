package gusev.max.presentation.fullphoto

import gusev.max.presentation.base.BaseResult
import gusev.max.presentation.base.model.TaskStatus
import gusev.max.presentation.model.UserModel


sealed class GetPhotoResult : BaseResult {

    class LoadFullPhoto(
        val taskStatus: TaskStatus,
        val user: UserModel? = null,
        val error: Throwable? = null
    ) : GetPhotoResult() {

        companion object {

            internal fun success(user: UserModel?): LoadFullPhoto {
                return LoadFullPhoto(
                        TaskStatus.SUCCESS,
                        user,
                        null
                )
            }

            internal fun failure(user: UserModel?): LoadFullPhoto {
                return LoadFullPhoto(
                        TaskStatus.FAILURE,
                        user
                )
            }

            internal fun inFlight(user: UserModel?): LoadFullPhoto {
                return LoadFullPhoto(
                        TaskStatus.IN_WORK,
                        user
                )
            }
        }
    }
}