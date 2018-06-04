package gusev.max.presentation.auth

import gusev.max.presentation.base.BaseViewState


sealed class AuthViewState(
    val inProgress: Boolean = false
) : BaseViewState {

    object InProgress : AuthViewState(true)

    object Failed : AuthViewState()

    object AuthSuccess : AuthViewState(false)

    class Idle : AuthViewState(false)
}