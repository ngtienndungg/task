package vn.dungnt.nothing.domain.entities

data class BookUiState(
    var bookList: List<BookEntity>? = null,
    var selectedBook: BookEntity? = null
)