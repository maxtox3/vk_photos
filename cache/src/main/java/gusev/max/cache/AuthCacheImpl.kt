package gusev.max.cache

import gusev.max.data.auth.AuthCache
import gusev.max.domain.model.AuthData
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject


class AuthCacheImpl @Inject constructor(
    private val preferencesHelper: PreferencesHelper
) : AuthCache {

    override fun saveAuthData(data: AuthData): Completable {
        return Completable.defer {
            preferencesHelper.accessToken = data.accessToken
            preferencesHelper.userId = data.userId
            preferencesHelper.tokenExpitationTime = data.expiresIn
            Completable.complete()
        }
    }

    override fun getAuthData(): Flowable<AuthData> {
        return Flowable.just(
                AuthData(
                        accessToken = preferencesHelper.accessToken,
                        userId = preferencesHelper.userId,
                        expiresIn = preferencesHelper.tokenExpitationTime
                )
        )
    }

    override fun getAccessToken(): String {
        return preferencesHelper.accessToken
    }

    override fun clearAuthData(): Completable {
        return Completable.defer {
            preferencesHelper.accessToken = ""
            preferencesHelper.userId = 0
            preferencesHelper.tokenExpitationTime = 0
            Completable.complete()
        }
    }

}