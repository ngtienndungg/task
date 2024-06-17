package vn.dungnt.nothing.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import vn.dungnt.nothing.data.base.NetworkResult
import vn.dungnt.nothing.data.base.transformToEntity
import vn.dungnt.nothing.data.mappers.BookListMapper
import vn.dungnt.nothing.data.mappers.BookMapper
import vn.dungnt.nothing.data.sources.local.BookLocalDataSource
import vn.dungnt.nothing.data.sources.remote.BookRemoteDataSource
import vn.dungnt.nothing.domain.entities.BookEntity
import vn.dungnt.nothing.domain.repositories.BookRepository
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val localDataSource: BookLocalDataSource,
    private val remoteDataSource: BookRemoteDataSource
) : BookRepository {
    private val _bookMapper = BookMapper()
    private val _bookListMapper = BookListMapper()
    override fun getBooks(): Flow<NetworkResult<List<BookEntity>>> {
        return remoteDataSource.getBooks().map { networkResult ->
            if (networkResult is NetworkResult.Success) {
                networkResult.data?.let {
                    localDataSource.saveBooks(it)
                }
            }
            networkResult.transformToEntity(_bookListMapper)
        }

    }

    override fun getBookDetail(id: Int): Flow<NetworkResult<BookEntity>> {
        return remoteDataSource.getBookDetail(id).map { networkResult ->
            if (networkResult is NetworkResult.Success) {
                networkResult.data?.let {
                    localDataSource.saveBook(it)
                }
            }
            networkResult.transformToEntity(_bookMapper)
        }
    }
}