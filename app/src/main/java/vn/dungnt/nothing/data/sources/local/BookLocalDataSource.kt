package vn.dungnt.nothing.data.sources.local

import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import vn.dungnt.nothing.data.base.NetworkResult
import vn.dungnt.nothing.data.models.BookDetailModel
import vn.dungnt.nothing.data.models.BookModel
import javax.inject.Inject

class BookLocalDataSource @Inject constructor(private val databaseHelper: DatabaseHelper) {
    suspend fun saveBook(book: BookDetailModel) = databaseHelper.insertOrUpdate(book)
    suspend fun saveBooks(books: List<BookModel>) = databaseHelper.insertOrUpdate(books)
    fun getBooks(): Flow<NetworkResult<List<BookModel>>> {
        val result: List<BookModel> = databaseHelper.findAll { realm ->
            realm.query<BookModel>()
        }
        return flowOf(NetworkResult.Success(data = result))
    }

    fun getBook(id: Int): Flow<NetworkResult<BookDetailModel?>> {
        val result: BookDetailModel? = databaseHelper.findFirst { realm ->
            realm.query<BookDetailModel>(
                "id = $0",
                id
            )
        }
        return flowOf(NetworkResult.Success(data = result))
    }
}