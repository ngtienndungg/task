package vn.dungnt.nothing.domain.repositories

import kotlinx.coroutines.flow.Flow
import vn.dungnt.nothing.data.base.NetworkResult
import vn.dungnt.nothing.domain.entities.BookDetailEntity
import vn.dungnt.nothing.domain.entities.BookEntity

interface BookRepository {
    fun getBooks(): Flow<NetworkResult<List<BookEntity>>>
    fun getBookDetail(id: Int): Flow<NetworkResult<BookDetailEntity>>
    suspend fun getBooksFromLocal(): List<BookEntity>?
    suspend fun getBookDetailFromLocal(id: Int): BookDetailEntity
}