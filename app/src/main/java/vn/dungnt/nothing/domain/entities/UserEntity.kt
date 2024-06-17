package vn.dungnt.nothing.domain.entities

data class UserEntity(
    val id: Int?,
    val username: String?,
    val password: String?,
    val accessToken: String?,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val avatar: String?
)