package gusev.max.remote.pojo

import com.google.gson.annotations.SerializedName


class BaseError{
    @SerializedName("error_code")
    var errorCode: Int? = null

    companion object {
        const val AUTH_ERROR_CODE = 5
    }
}