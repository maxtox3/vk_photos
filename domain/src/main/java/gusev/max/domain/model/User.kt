package gusev.max.domain.model


data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val online: Boolean,
    val fullPhotoId: String,
    val photo100Url: String
) : BaseDomainModel