package gusev.max.domain.model

data class Photo(
    val userId: Long,
    val fullPhotoId: String,
    val photo100Url: String,
    val fullPhotoPath: String?,
    val photo100path: String?
) : BaseDomainModel