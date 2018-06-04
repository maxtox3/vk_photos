package gusev.max.presentation.fullphoto

import gusev.max.presentation.base.BaseAction
import gusev.max.presentation.model.UserModel


sealed class GetPhotoAction : BaseAction {

    class LoadPhoto(val user: UserModel?) : GetPhotoAction()
}