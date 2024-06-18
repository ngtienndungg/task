package vn.dungnt.nothing.data.sources.remote

import kotlinx.coroutines.flow.Flow
import vn.dungnt.nothing.data.base.NetworkResult
import vn.dungnt.nothing.data.base.getNetworkResult
import vn.dungnt.nothing.data.base.resultFlow
import vn.dungnt.nothing.data.models.BookDetailModel
import vn.dungnt.nothing.data.models.BookModel
import javax.inject.Inject

class BookRemoteDataSource @Inject constructor(private val apiService: APIService) {
    fun getBooks(): Flow<NetworkResult<List<BookModel>>> = resultFlow {
        getNetworkResult {
            apiService.getBooks()
        }
    }

    fun getBookDetail(id: Int): Flow<NetworkResult<BookDetailModel>> = resultFlow {
        getNetworkResult {
            apiService.getBookDetail(id)
        }
    }
}