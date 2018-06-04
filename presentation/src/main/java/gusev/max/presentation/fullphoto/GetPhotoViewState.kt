package gusev.max.presentation.fullphoto

import gusev.max.presentation.base.BaseViewState
import gusev.max.presentation.model.UserModel



sealed class GetPhotoViewState(
    val inProgress: Boolean = false,
    val userModel: UserModel? = null
) : BaseViewState {

    class InProgress(userModel: UserModel?) : GetPhotoViewState(true, userModel)

    class Failed(userModel: UserModel?) : GetPhotoViewState(false, userModel)

    class LoadPhotoSuccess(userModel: UserModel?) :
            GetPhotoViewState(false, userModel)

    class Idle : GetPhotoViewState(false, null)
}