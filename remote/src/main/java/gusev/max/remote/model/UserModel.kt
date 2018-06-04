package gusev.max.remote.model

import com.google.gson.annotations.SerializedName


data class UserModel(
    val id: Long,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("photo_100")
    val photo100Url: String?,
    @SerializedName("photo_max_orig")
    val photoId: String?,
    val online: Int
) : BaseRemoteModel