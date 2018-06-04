package gusev.max.presentation.mapper

import gusev.max.domain.model.AuthData


object AuthDataMapper {

    private const val PARAMS_IN_URL_INDEX = 1
    private const val ACCESS_TOKEN_INDEX = 0
    private const val INDEX_OF_VALUE_AFTER_EQUAL_SIGN = 1
    private const val EXPIRES_IN_INDEX = 1
    private const val USER_ID_INDEX = 2

    fun mapUrlToAuthData(url: String): AuthData {
        val params = getParams(url)
        return AuthData(
                accessToken = getAccessToken(params),
                expiresIn = getExpiresIn(params),
                userId = getUserId(params)
        )
    }

    private fun getParams(url: String): List<String> {
        return splitBySplitter(
                string = splitBySplitter(url, "#")[PARAMS_IN_URL_INDEX],
                splitter = "&"
        )
    }

    private fun getAccessToken(params: List<String>): String {
        return getValueFromKeyValue(params[ACCESS_TOKEN_INDEX])
    }

    private fun getExpiresIn(params: List<String>): Long {
        return getValueFromKeyValue(params[EXPIRES_IN_INDEX]).toLong()
    }

    private fun getUserId(params: List<String>): Long {
        return getValueFromKeyValue(params[USER_ID_INDEX]).toLong()
    }

    private fun getValueFromKeyValue(keyValue: String): String {
        return splitBySplitter(keyValue, "=")[INDEX_OF_VALUE_AFTER_EQUAL_SIGN]
    }

    private fun splitBySplitter(string: String, splitter: String): List<String> {
        return string.split(splitter.toRegex()).dropLastWhile { it.isEmpty() }
    }
}