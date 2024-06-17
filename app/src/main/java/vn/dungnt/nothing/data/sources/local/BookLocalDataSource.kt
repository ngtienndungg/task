package vn.dungnt.nothing.data.sources.local

import vn.dungnt.nothing.data.models.BookModel
import javax.inject.Inject

class BookLocalDataSource @Inject constructor(private val databaseHelper: DatabaseHelper) {
    suspend fun saveBook(book: BookModel) = databaseHelper.insertOrUpdate(book)
    suspend fun saveBooks(books: List<BookModel>) = databaseHelper.insertOrUpdate(books)
}