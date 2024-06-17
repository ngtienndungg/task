package vn.dungnt.nothing.data.sources.local

import io.realm.kotlin.ext.query
import vn.dungnt.nothing.data.models.UserModel
import javax.inject.Inject

class LoginLocalDataSource @Inject constructor(private val databaseHelper: DatabaseHelper) {
    suspend fun saveUser(user: UserModel) = databaseHelper.insertOrUpdate(user)
    fun getUser(username: String) = databaseHelper.findFirst {
        it.query<UserModel>().query("id = $0", username)
    }
}