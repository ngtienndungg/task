package vn.dungnt.nothing.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vn.dungnt.nothing.data.base.handleNetworkResult
import vn.dungnt.nothing.domain.entities.BookEntity
import vn.dungnt.nothing.domain.entities.BookUiState
import vn.dungnt.nothing.domain.entities.UiState
import vn.dungnt.nothing.domain.usecases.BookUseCase
import vn.dungnt.nothing.presentation.viewmodels.base.BaseViewModel
import vn.dungnt.nothing.utils.Utils.isNetworkAvailable
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(private val bookUseCase: BookUseCase) : BaseViewModel() {

    private val _bookState = MutableStateFlow(BookUiState())
    val bookState = _bookState.asStateFlow()

    private fun setBookList(bookList: List<BookEntity>) {
        _bookState.update { currentState ->
            currentState.copy(bookList = bookList)
        }
    }

    fun getBooks() {
        viewModelScope.launch {
            if (isNetworkAvailable()) {
                bookUseCase.getBooks().collectLatest { networkResult ->
                    networkResult.handleNetworkResult(
                        onSuccess = {
                            setBookList(it.data ?: emptyList())
                        },
                        onFailure = { failure ->
                            setUiState(UiState.Error(failure.message))
                        },
                        onLoading = {
                            setUiState(if (it) UiState.Loading else UiState.None)
                        }
                    )
                }
            } else {
                val bookList = bookUseCase.getBooksFromLocal()
                if (bookList != null) {
                    setBookList(bookList)
                }
            }
        }
    }
}