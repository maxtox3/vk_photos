package gusev.max.presentation.fullphoto

import gusev.max.presentation.base.BaseIntent
import gusev.max.presentation.model.UserModel


sealed class GetPhotoIntent : BaseIntent {

    object InitialIntent : GetPhotoIntent()

    class LoadFullPhoto(val user: UserModel) : GetPhotoIntent()

    class TryAgain(val user: UserModel) : GetPhotoIntent()
}