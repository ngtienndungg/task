package vn.dungnt.nothing.data.models

import com.google.gson.annotations.SerializedName
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class BookModel : RealmObject {

    @PrimaryKey
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("subtitle")
    var subtitle: String? = null

    @SerializedName("category")
    var category: String? = null

    @SerializedName("avatar")
    var avatar: String? = null
}

class BookDetailModel : RealmObject {
    @PrimaryKey
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("subtitle")
    var subtitle: String? = null

    @SerializedName("category")
    var category: String? = null

    @SerializedName("author")
    var author: String? = null

    @SerializedName("published_year")
    var publishedYear: Int? = null

    @SerializedName("isbn")
    var isbn: String? = null

    @SerializedName("summary")
    var summary: String? = null

    @SerializedName("avatar")
    var avatar: String? = null
}