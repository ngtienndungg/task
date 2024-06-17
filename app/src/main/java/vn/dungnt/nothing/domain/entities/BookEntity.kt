package vn.dungnt.nothing.domain.entities

import com.google.gson.annotations.SerializedName

data class BookEntity(
    val id: Int? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val category: String? = null,
    val author: String? = null,
    val publishedYear: Int? = null,
    val isbn: String? = null,
    val summary: String? = null,
    val avatar: String? = null
)

data class BookListEntity(
    @SerializedName("books")
    val books: List<BookEntity>? = null
)
