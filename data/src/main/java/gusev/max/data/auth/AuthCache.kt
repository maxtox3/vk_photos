package gusev.max.data.auth

import gusev.max.domain.model.AuthData
import io.reactivex.Completable
import io.reactivex.Flowable

interface AuthCache {

    fun saveAuthData(data: AuthData): Completable

    fun getAuthData(): Flowable<AuthData>

    fun getAccessToken(): String

    fun clearAuthData(): Completable
}