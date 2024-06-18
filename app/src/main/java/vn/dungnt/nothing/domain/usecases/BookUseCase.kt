package vn.dungnt.nothing.domain.usecases

import kotlinx.coroutines.flow.Flow
import vn.dungnt.nothing.data.base.NetworkResult
import vn.dungnt.nothing.domain.entities.BookDetailEntity
import vn.dungnt.nothing.domain.entities.BookEntity
import vn.dungnt.nothing.domain.repositories.BookRepository
import javax.inject.Inject

class BookUseCase @Inject constructor(private val bookRepository: BookRepository) {
    fun getBooks(): Flow<NetworkResult<List<BookEntity>>> = bookRepository.getBooks()
    suspend fun getBooksFromLocal(): List<BookEntity>? = bookRepository.getBooksFromLocal()
    fun getBookDetail(id: Int): Flow<NetworkResult<BookDetailEntity>> = bookRepository.getBookDetail(id)
    suspend fun getBookDetailFromLocal(id: Int): BookDetailEntity = bookRepository.getBookDetailFromLocal(id)
}