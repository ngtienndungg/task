package vn.dungnt.nothing.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import vn.dungnt.nothing.data.base.handleNetworkResult
import vn.dungnt.nothing.domain.entities.BookEntity
import vn.dungnt.nothing.domain.entities.UiState
import vn.dungnt.nothing.domain.usecases.BookUseCase
import vn.dungnt.nothing.presentation.viewmodels.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(private val bookUseCase: BookUseCase) : BaseViewModel() {

    init {
        getBooks()
    }

    private val _bookListState = MutableStateFlow<List<BookEntity>>(emptyList())
    val bookListState = _bookListState.asStateFlow()

    private fun setBookList(bookList: List<BookEntity>) {
        _bookListState.value = bookList
    }


    private fun getBooks() {
        viewModelScope.launch {
            bookUseCase.getBooks().collectLatest { networkResult ->
                networkResult.handleNetworkResult(
                    onSuccess = {
                        setBookList(it.data ?: emptyList())
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