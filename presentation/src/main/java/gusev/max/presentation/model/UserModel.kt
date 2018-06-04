package gusev.max.presentation.model

import android.graphics.Bitmap
import gusev.max.presentation.base.model.BaseModel
import java.io.Serializable


data class UserModel(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val online: Boolean,
    val fullPhotoId: String,
    val photo100Url: String
) : BaseModel, Serializable {

    @Transient
    var bitmap: Bitmap? = null
    var error: Boolean = false
    var inProgress: Boolean = false

    fun getName(): String {
        return "$firstName $lastName"
    }
}