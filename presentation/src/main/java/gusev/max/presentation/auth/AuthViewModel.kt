package gusev.max.presentation.auth

import android.arch.lifecycle.ViewModel
import gusev.max.presentation.base.BaseViewModel
import gusev.max.presentation.base.model.TaskStatus
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


class AuthViewModel @Inject constructor(
    private val processor: AuthProcessor
) : ViewModel(), BaseViewModel<AuthIntent, AuthViewState> {

    private var intentsSubject: PublishSubject<AuthIntent> = PublishSubject.create()
    private val intentFilter: ObservableTransformer<AuthIntent, AuthIntent> =
        ObservableTransformer {
            it.publish {
                Observable.merge(
                        it.ofType(AuthIntent.InitialIntent::class.java).take(1),
                        it.filter({ intent -> intent !== AuthIntent.InitialIntent })
                )
            }

        }
    private val reducer: BiFunction<AuthViewState, AuthResult, AuthViewState> =
        BiFunction { previousState, result ->
            when (result) {
                is AuthResult.SaveAuth -> {
                    when {
                        result.taskStatus == TaskStatus.SUCCESS -> AuthViewState.AuthSuccess
                        result.taskStatus == TaskStatus.FAILURE -> AuthViewState.Failed
                        result.taskStatus == TaskStatus.IN_WORK -> AuthViewState.InProgress
                        else -> AuthViewState.Idle()
                    }
                }
            }
        }
    private val statesSubject: Observable<AuthViewState> = compose()

    override fun processIntents(intents: Observable<AuthIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): Observable<AuthViewState> {
        return statesSubject
    }

    private fun compose(): Observable<AuthViewState> {
        return intentsSubject
            .compose(intentFilter)
            .map { this.actionFromIntent(it) }
            .compose(processor.actionProcessor)
            .scan<AuthViewState>(AuthViewState.Idle(), reducer)
            .replay(1)
            .autoConnect(0)
    }

    private fun actionFromIntent(intent: AuthIntent): AuthAction {
        return when (intent) {
            is AuthIntent.SaveAuthDataIntent -> AuthAction.SaveAuthData(intent.url)
            is AuthIntent.InitialIntent -> AuthAction.SaveAuthData(null)

            else -> throw UnsupportedOperationException("Oops, that looks like an unknown intent: $intent")
        }
    }


}