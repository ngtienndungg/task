package vn.dungnt.nothing.data.sources.local

import io.realm.kotlin.ext.query
import vn.dungnt.nothing.data.base.getLocalResult
import vn.dungnt.nothing.data.base.resultFlow
import vn.dungnt.nothing.data.models.UserModel
import javax.inject.Inject

class LoginLocalDataSource @Inject constructor(private val databaseHelper: DatabaseHelper) {
    suspend fun saveUser(user: UserModel) = databaseHelper.insertOrUpdate(user)
    fun getUser(username: String) = resultFlow(true) {
        getLocalResult {
            databaseHelper.findFirst {
                it.query<UserModel>().query("username = $0", username)
            }
        }
    }

    suspend fun deleteUser(username: String) = databaseHelper.deleteByQuery {
        it.query<UserModel>().query("username = $0", username)
    }
}