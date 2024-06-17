package vn.dungnt.nothing.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import vn.dungnt.nothing.data.base.NetworkResult
import vn.dungnt.nothing.data.base.transformToEntity
import vn.dungnt.nothing.data.mappers.UserMapper
import vn.dungnt.nothing.data.models.LoginRequest
import vn.dungnt.nothing.data.sources.local.LoginLocalDataSource
import vn.dungnt.nothing.data.sources.remote.LoginRemoteDataSource
import vn.dungnt.nothing.domain.entities.UserEntity
import vn.dungnt.nothing.domain.repositories.LoginRepository
import vn.dungnt.nothing.utils.Constants
import vn.dungnt.nothing.utils.SharedPrefs
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val localDataSource: LoginLocalDataSource,
    private val remoteDataSource: LoginRemoteDataSource
) : LoginRepository {
    private val _userMapper = UserMapper()
    override fun login(loginRequest: LoginRequest): Flow<NetworkResult<UserEntity>> {
        return remoteDataSource.login(loginRequest).map { networkResult ->
            if (networkResult is NetworkResult.Success) {
                networkResult.data?.let {
                    SharedPrefs.saveString(
                        Constants.PREFS_ACCESS_TOKEN,
                        networkResult.data.accessToken
                    )
                    localDataSource.saveUser(it)
                }
            }
            networkResult.transformToEntity(_userMapper)
        }
    }

    override suspend fun getCurrentUser(username: String): UserEntity? {
        val userModel = localDataSource.getUser(username)
        return userModel?.let { _userMapper.toEntity(it) }
    }
}