package gusev.max.domain.model


data class AuthData(
    val accessToken: String,
    val expiresIn: Long,
    val userId: Long
)