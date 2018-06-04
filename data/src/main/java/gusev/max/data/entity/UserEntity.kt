package gusev.max.data.entity


data class UserEntity(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val online: Boolean,
    val fullPhotoId: String,
    val photo100Url: String
) : BaseEntity