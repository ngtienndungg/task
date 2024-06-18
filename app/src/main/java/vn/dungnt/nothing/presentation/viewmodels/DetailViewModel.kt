package vn.dungnt.nothing.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vn.dungnt.nothing.data.base.handleNetworkResult
import vn.dungnt.nothing.domain.entities.BookDetailEntity
import vn.dungnt.nothing.domain.entities.BookEntity
import vn.dungnt.nothing.domain.entities.DetailUiState
import vn.dungnt.nothing.domain.entities.UiState
import vn.dungnt.nothing.domain.usecases.BookUseCase
import vn.dungnt.nothing.presentation.viewmodels.base.BaseViewModel
import vn.dungnt.nothing.utils.Utils.isNetworkAvailable
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val bookUseCase: BookUseCase) : BaseViewModel() {

    private val _detailState = MutableStateFlow(DetailUiState())
    val detailState = _detailState.asStateFlow()

    private fun setBookDetail(book: BookDetailEntity) {
        _detailState.update { currentState ->
            currentState.copy(bookDetail = book)
        }
    }

    fun getBookDetail(id: Int) {
        viewModelScope.launch {
            bookUseCase.getBookDetail(id).collectLatest { networkResult ->
                if (isNetworkAvailable()) {
                    networkResult.handleNetworkResult(
                        onSuccess = {
                            it.data?.let { it1 -> setBookDetail(it1) }
                        },
                        onFailure = { failure ->
                            setUiState(UiState.Error(failure.message))
                        },
                        onLoading = {
                            setUiState(if (it) UiState.Loading else UiState.None)
                        }
                    )
                } else {
                    setBookDetail(bookUseCase.getBookDetailFromLocal(id))
                }
            }

        }
    }
}