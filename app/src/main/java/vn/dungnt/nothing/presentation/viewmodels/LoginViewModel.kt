package vn.dungnt.nothing.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import vn.dungnt.nothing.R
import vn.dungnt.nothing.application
import vn.dungnt.nothing.data.base.handleNetworkResult
import vn.dungnt.nothing.data.models.LoginRequest
import vn.dungnt.nothing.domain.entities.UiState
import vn.dungnt.nothing.domain.usecases.LoginUseCase
import vn.dungnt.nothing.presentation.viewmodels.base.BaseViewModel
import vn.dungnt.nothing.utils.Utils.getString
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : BaseViewModel() {
    fun login(username: String, password: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            if (username.isEmpty() || password.isEmpty()) {
                setUiState(UiState.Error(getString(application!!, R.string.input_please)))
            } else {
                loginUseCase.login(LoginRequest(username, password))
                    .collectLatest { networkResult ->
                        networkResult.handleNetworkResult(
                            onSuccess = {
                                onResult.invoke(true, "")
                                setUiState(UiState.None)
                            },
                            onFailure = { failure ->
                                setUiState(UiState.Error(failure.message))
                            },
                            onLoading = {
                                setUiState(if (it) UiState.Loading else UiState.None)
                            }
                        )
                    }
            }
        }

    }
}