package vn.dungnt.nothing.presentation.viewmodels

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import vn.dungnt.nothing.domain.entities.UserEntity
import vn.dungnt.nothing.domain.usecases.LoginUseCase
import vn.dungnt.nothing.presentation.viewmodels.base.BaseViewModel
import javax.inject.Inject

class AccountViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : BaseViewModel() {

    private val _accountState = MutableStateFlow(UserEntity())
    val bookState = _bookState.asStateFlow()

    private fun getCurrentUser(username: String) {
        viewModelScope.launch {
            loginUseCase.getCurrentUser(username) }
    }
}