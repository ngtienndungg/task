package vn.dungnt.nothing.data.base

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    val status: String?,
    val message: String?,
    val data: NestedResponse<T>?
)

data class NestedResponse<T>(
    @SerializedName("data", alternate = ["user", "bookDetail", "books"])
    val user: T
)