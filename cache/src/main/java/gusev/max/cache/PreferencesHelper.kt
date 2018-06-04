package gusev.max.cache

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
open class
PreferencesHelper @Inject constructor(context: Context) {

    companion object {
        private const val PREFS_PACKAGE_NAME = "gusev.max.vkphotos.preferences"

        private const val LAST_USERS_CACHE_DATE = "last_users_cache"
        private const val ACCESS_TOKEN = "access_token"
        private const val USER_ID = "user_id"
        private const val TOKEN_EXPIRATION_TIME = "token_expiration_time"
        private const val PERMISSIONS_GRANTED = "permissions_granted"
    }

    private val prefs: SharedPreferences

    init {
        prefs = context.getSharedPreferences(PREFS_PACKAGE_NAME, Context.MODE_PRIVATE)
    }

    var lastUsersChangeTime: Long
        get() = getTime(LAST_USERS_CACHE_DATE)
        set(value) = setTime(LAST_USERS_CACHE_DATE, value)

    var accessToken: String
        get() = prefs.getString(ACCESS_TOKEN, "")
        set(value) = prefs.edit().putString(ACCESS_TOKEN, value).apply()

    var userId: Long
        get() = prefs.getLong(USER_ID, -1)
        set(value) = prefs.edit().putLong(USER_ID, value).apply()

    var tokenExpitationTime: Long
        get() = prefs.getLong(TOKEN_EXPIRATION_TIME, -1)
        set(value) = prefs.edit().putLong(TOKEN_EXPIRATION_TIME, value).apply()

    var permissionsGranted: Boolean
        get() = prefs.getBoolean(PERMISSIONS_GRANTED, false)
        set(value) = prefs.edit().putBoolean(PERMISSIONS_GRANTED, value).apply()

    private fun getTime(key: String): Long {
        return prefs.getLong(key, 0L)
    }

    private fun setTime(key: String, value: Long) {
        prefs.edit().putLong(key, value).apply()
    }


}