package vn.dungnt.nothing.data.repositories

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import vn.dungnt.nothing.data.base.NetworkResult
import vn.dungnt.nothing.data.base.transformToEntity
import vn.dungnt.nothing.data.mappers.BookDetailMapper
import vn.dungnt.nothing.data.mappers.BookListMapper
import vn.dungnt.nothing.data.mappers.BookMapper
import vn.dungnt.nothing.data.sources.local.BookLocalDataSource
import vn.dungnt.nothing.data.sources.remote.BookRemoteDataSource
import vn.dungnt.nothing.domain.entities.BookDetailEntity
import vn.dungnt.nothing.domain.entities.BookEntity
import vn.dungnt.nothing.domain.repositories.BookRepository
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val localDataSource: BookLocalDataSource,
    private val remoteDataSource: BookRemoteDataSource
) : BookRepository {
    private val _bookMapper = BookMapper()
    private val _bookListMapper = BookListMapper()
    private val _bookDetailMapper = BookDetailMapper()
    override fun getBooks(): Flow<NetworkResult<List<BookEntity>>> {
        return remoteDataSource.getBooks().map { networkResult ->
            if (networkResult is NetworkResult.Success) {
                networkResult.data?.let {
                    localDataSource.saveBooks(it)
                    for (book in it) {
                        Log.d("BookRepositoryImpl", "getBooks: ${book.id}")
                    }
                }
            }
            networkResult.transformToEntity(_bookListMapper)
        }

    }

    override fun getBookDetail(id: Int): Flow<NetworkResult<BookDetailEntity>> {
        return remoteDataSource.getBookDetail(id).map { networkResult ->
            if (networkResult is NetworkResult.Success) {
                networkResult.data?.let {
                    localDataSource.saveBook(it)
                }
            }
            networkResult.transformToEntity(_bookDetailMapper)
        }
    }

    override suspend fun getBooksFromLocal(): List<BookEntity> {
        val bookListModel = localDataSource.getBooks()
        return bookListModel.let { _bookListMapper.toEntity(it) }
    }

    override suspend fun getBookDetailFromLocal(id: Int): BookDetailEntity {
        val bookModel = localDataSource.getBook(id)
        return bookModel?.let { _bookDetailMapper.toEntity(it) } ?: BookDetailEntity()
    }
}