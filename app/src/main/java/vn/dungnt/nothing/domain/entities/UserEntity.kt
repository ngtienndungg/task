package vn.dungnt.nothing.domain.entities

data class UserEntity(
    val id: Int? = null,
    val username: String? = null,
    val password: String? = null,
    val accessToken: String? = null,
    val email: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val avatar: String? = null
)