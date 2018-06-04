package gusev.max.domain.repository

import gusev.max.domain.model.AuthData
import io.reactivex.Completable


interface AuthRepository {

    fun saveAuthData(data: AuthData): Completable

    fun clearAuthData(): Completable
}