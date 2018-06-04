package gusev.max.presentation.auth

import gusev.max.presentation.base.BaseIntent


sealed class AuthIntent : BaseIntent {

    object InitialIntent : AuthIntent()

    class SaveAuthDataIntent(val url: String) : AuthIntent()

}