package gusev.max.presentation.base

import io.reactivex.Observable


interface BaseView<INTENT: BaseIntent, in STATE: BaseViewState> {
    fun intents(): Observable<INTENT>

    fun render(state: STATE)
}