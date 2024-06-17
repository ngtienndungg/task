package vn.dungnt.nothing.data.sources.remote

import kotlinx.coroutines.flow.Flow
import vn.dungnt.nothing.data.base.NetworkResult
import vn.dungnt.nothing.data.base.getNetworkResult
import vn.dungnt.nothing.data.base.resultFlow
import vn.dungnt.nothing.data.models.LoginRequest
import vn.dungnt.nothing.data.models.UserModel
import javax.inject.Inject

class LoginRemoteDataSource @Inject constructor(private val apiService: APIService) {
    fun login(loginRequest: LoginRequest): Flow<NetworkResult<UserModel>> = resultFlow {
        getNetworkResult {
            apiService.login(loginRequest)
        }
    }
}