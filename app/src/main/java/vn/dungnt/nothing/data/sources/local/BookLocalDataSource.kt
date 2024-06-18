package vn.dungnt.nothing.data.sources.local

import io.realm.kotlin.ext.query
import vn.dungnt.nothing.data.models.BookDetailModel
import vn.dungnt.nothing.data.models.BookModel
import javax.inject.Inject

class BookLocalDataSource @Inject constructor(private val databaseHelper: DatabaseHelper) {
    suspend fun saveBook(book: BookDetailModel) = databaseHelper.insertOrUpdate(book)
    suspend fun saveBooks(books: List<BookModel>) = databaseHelper.insertOrUpdate(books)
    fun getBooks() = databaseHelper.findAll { realm ->
        realm.query<BookModel>()
    }

    fun getBook(id: Int) =
        databaseHelper.findFirst { realm ->
            realm.query<BookDetailModel>(
                "id = $0",
                id
            )
        }
}