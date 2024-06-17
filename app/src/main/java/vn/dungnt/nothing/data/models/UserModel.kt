package vn.dungnt.nothing.data.models

import com.google.gson.annotations.SerializedName
import io.realm.kotlin.types.RealmObject

class UserModel : RealmObject {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("username")
    var username: String? = null

    @SerializedName("password")
    var password: String? = null

    @SerializedName("accessToken")
    var accessToken: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("firstName")
    var firstName: String? = null

    @SerializedName("lastName")
    var lastName: String? = null

    @SerializedName("avatar")
    var avatar: String? = null
}