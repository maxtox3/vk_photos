package gusev.max.presentation.auth

import gusev.max.domain.interactor.SaveAuthUserData
import gusev.max.presentation.mapper.AuthDataMapper
import io.reactivex.ObservableTransformer
import javax.inject.Inject


class AuthProcessor @Inject constructor(
    private val saveUserData: SaveAuthUserData,
    private val mapper: AuthDataMapper
) {
    private val saveProcessor: ObservableTransformer<AuthAction.SaveAuthData, AuthResult> = ObservableTransformer {
        it
            .filter {
                it.url != null
            }
            .map {
                mapper.mapUrlToAuthData(it.url!!)
            }
            .switchMap {
                saveUserData.execute(it)
                    .toSingleDefault(
                            AuthResult.SaveAuth.success()
                    )
                    .onErrorReturn {
                        AuthResult.SaveAuth.failure()
                    }
                    .toObservable()
                    .startWith(AuthResult.SaveAuth.inFlight())
            }
    }
    var actionProcessor: ObservableTransformer<AuthAction, AuthResult>

    init {
        this.actionProcessor = ObservableTransformer {
            it.publish {
                it.ofType(AuthAction.SaveAuthData::class.java)
                    .compose(saveProcessor)
            }
        }
    }
}