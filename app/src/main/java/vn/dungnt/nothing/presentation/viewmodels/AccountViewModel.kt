package vn.dungnt.nothing.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import vn.dungnt.nothing.data.base.handleNetworkResult
import vn.dungnt.nothing.domain.entities.UiState
import vn.dungnt.nothing.domain.entities.UserEntity
import vn.dungnt.nothing.domain.usecases.LoginUseCase
import vn.dungnt.nothing.presentation.viewmodels.base.BaseViewModel
import vn.dungnt.nothing.utils.Constants
import vn.dungnt.nothing.utils.SharedPrefs
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val loginUseCase: LoginUseCase) :
    BaseViewModel() {

    private val _userState = MutableStateFlow(UserEntity())
    val userState = _userState.asStateFlow()

    private fun setUser(user: UserEntity) {
        _userState.value = user
    }

    fun getCurrentUser(username: String) {
        viewModelScope.launch {
            loginUseCase.getCurrentUser(username).collectLatest { result ->
                result.handleNetworkResult(
                    onSuccess = {
                        it.data?.let { it1 -> setUser(it1) }
                    },
                    onLoading = {
                        setUiState(if (it) UiState.Loading else UiState.None)
                    },
                    onFailure = {
                        setUiState(UiState.Error(it.data.toString()))
                    }
                )
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            loginUseCase.logout(SharedPrefs.getString(Constants.PREFS_USERNAME, ""))
        }
    }
}