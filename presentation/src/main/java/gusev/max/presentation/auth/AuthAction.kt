package gusev.max.presentation.auth

import gusev.max.presentation.base.BaseAction


sealed class AuthAction : BaseAction {

    class SaveAuthData(val url: String?) : AuthAction()
}