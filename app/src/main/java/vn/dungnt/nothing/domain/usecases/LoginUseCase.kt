package vn.dungnt.nothing.domain.usecases

import kotlinx.coroutines.flow.Flow
import vn.dungnt.nothing.data.base.NetworkResult
import vn.dungnt.nothing.data.models.LoginRequest
import vn.dungnt.nothing.domain.entities.UserEntity
import vn.dungnt.nothing.domain.repositories.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: LoginRepository) {
    fun login(loginRequest: LoginRequest): Flow<NetworkResult<UserEntity>> =
        repository.login(loginRequest)

    suspend fun getCurrentUser(username: String): Flow<NetworkResult<UserEntity>> =
        repository.getCurrentUser(username)

    suspend fun logout(username: String) = repository.logout(username)
}