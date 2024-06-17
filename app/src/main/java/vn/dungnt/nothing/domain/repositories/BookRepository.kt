package vn.dungnt.nothing.domain.repositories

import kotlinx.coroutines.flow.Flow
import vn.dungnt.nothing.data.base.NetworkResult
import vn.dungnt.nothing.data.models.BookModel
import vn.dungnt.nothing.domain.entities.BookEntity

interface BookRepository {
    fun getBooks(): Flow<NetworkResult<List<BookEntity>>>
    fun getBookDetail(id: Int): Flow<NetworkResult<BookEntity>>
}