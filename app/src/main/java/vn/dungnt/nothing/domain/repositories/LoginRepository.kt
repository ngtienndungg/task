package vn.dungnt.nothing.domain.repositories

import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import vn.dungnt.nothing.data.base.NetworkResult
import vn.dungnt.nothing.data.models.LoginRequest
import vn.dungnt.nothing.domain.entities.UserEntity

interface LoginRepository {
    fun login(@Body loginRequest: LoginRequest): Flow<NetworkResult<UserEntity>>
    suspend fun getCurrentUser(username: String): Flow<NetworkResult<UserEntity>>
    suspend fun logout(username: String): Flow<NetworkResult<Any>>
}