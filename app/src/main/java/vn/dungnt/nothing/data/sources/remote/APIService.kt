package vn.dungnt.nothing.data.sources.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import vn.dungnt.nothing.data.base.ApiResponse
import vn.dungnt.nothing.data.models.BookDetailModel
import vn.dungnt.nothing.data.models.BookModel
import vn.dungnt.nothing.data.models.LoginRequest
import vn.dungnt.nothing.data.models.UserModel

interface APIService {
    @POST("mocks/a4458f88-eac8-4c40-887e-be411b6dcea4/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<ApiResponse<UserModel>>

    @GET("mocks/a4458f88-eac8-4c40-887e-be411b6dcea4/books")
    suspend fun getBooks(): Response<ApiResponse<List<BookModel>>>

    @GET("mocks/a4458f88-eac8-4c40-887e-be411b6dcea4/bookDetail")
    suspend fun getBookDetail(@Query("id") id: Int): Response<ApiResponse<BookDetailModel>>

    @GET("mocks/a4458f88-eac8-4c40-887e-be411b6dcea4/logout")
    suspend fun logoutUser(): Response<ApiResponse<ApiResponse<UserModel>>>
}