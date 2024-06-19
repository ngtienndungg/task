package vn.dungnt.nothing.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import vn.dungnt.nothing.data.base.handleNetworkResult
import vn.dungnt.nothing.domain.entities.BookDetailEntity
import vn.dungnt.nothing.domain.entities.UiState
import vn.dungnt.nothing.domain.usecases.BookUseCase
import vn.dungnt.nothing.presentation.viewmodels.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val bookUseCase: BookUseCase) : BaseViewModel() {

    private val _detailState = MutableStateFlow(BookDetailEntity())
    val detailState = _detailState.asStateFlow()

    private fun setBookDetail(bookEntity: BookDetailEntity) {
        _detailState.value = bookEntity
    }

    fun getBookDetail(id: Int) {
        viewModelScope.launch {
            bookUseCase.getBookDetail(id).collectLatest { networkResult ->
                networkResult.handleNetworkResult(
                    onSuccess = {
                        it.data?.let { it1 -> setBookDetail(it1) }
                        Log.d("TAG", "getBookDetail: $it")
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