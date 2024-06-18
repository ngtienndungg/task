package vn.dungnt.nothing.domain.entities

import com.google.gson.annotations.SerializedName

data class BookEntity(
    val id: Int? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val category: String? = null,
    val avatar: String? = null
)

data class BookListEntity(
    @SerializedName("books")
    val books: List<BookEntity>? = null
)

data class BookDetailEntity(
    var id: Int? = null,
    var title: String? = null,
    var subtitle: String? = null,
    var category: String? = null,
    var author: String? = null,
    var publishedYear: Int? = null,
    var isbn: String? = null,
    var summary: String? = null,
    var avatar: String? = null
)
