package vn.dungnt.nothing.data.sources.local

import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import vn.dungnt.nothing.data.base.NetworkResult
import vn.dungnt.nothing.data.base.getLocalResult
import vn.dungnt.nothing.data.base.resultFlow
import vn.dungnt.nothing.data.models.BookDetailModel
import vn.dungnt.nothing.data.models.BookModel
import javax.inject.Inject

class BookLocalDataSource @Inject constructor(private val databaseHelper: DatabaseHelper) {
    suspend fun saveBook(book: BookDetailModel) = databaseHelper.insertOrUpdate(book)
    suspend fun saveBooks(books: List<BookModel>) = databaseHelper.insertOrUpdate(books)
    fun getBooks(): Flow<NetworkResult<List<BookModel>>> = resultFlow(true) {
        getLocalResult {
            val result: List<BookModel> = databaseHelper.findAll { realm ->
                realm.query<BookModel>()
            }
            result
        }
    }

    fun getBook(id: Int): Flow<NetworkResult<BookDetailModel?>> = resultFlow(true) {
        getLocalResult {
            val result: BookDetailModel? = databaseHelper.findFirst { realm ->
                realm.query<BookDetailModel>(
                    "id = $0",
                    id
                )
            }
            result
        }
    }
}