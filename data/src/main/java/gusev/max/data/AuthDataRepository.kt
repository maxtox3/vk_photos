package gusev.max.data

import gusev.max.data.auth.AuthCache
import gusev.max.domain.model.AuthData
import gusev.max.domain.repository.AuthRepository
import io.reactivex.Completable
import javax.inject.Inject


class AuthDataRepository @Inject constructor(
    private val cache: AuthCache
) : AuthRepository {

    override fun saveAuthData(data: AuthData): Completable {
        return cache.saveAuthData(data)
    }

    override fun clearAuthData(): Completable {
        return cache.clearAuthData()
    }

}