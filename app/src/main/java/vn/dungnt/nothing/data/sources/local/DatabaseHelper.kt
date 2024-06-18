package vn.dungnt.nothing.data.sources.local

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.copyFromRealm
import io.realm.kotlin.query.RealmQuery
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject
import vn.dungnt.nothing.data.models.BookDetailModel
import vn.dungnt.nothing.data.models.BookModel
import vn.dungnt.nothing.data.models.UserModel

class DatabaseHelper {
    private val config =
        RealmConfiguration.Builder(schema = setOf(UserModel::class, BookModel::class, BookDetailModel::class))
            .schemaVersion(1).name("app_database.db").deleteRealmIfMigrationNeeded().build()
    val realm = Realm.open(config)

    suspend fun <T : RealmObject> insertOrUpdate(data: T?) {
        data?.let {
            try {
                realm.write {
                    copyToRealm(it, UpdatePolicy.ALL)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    suspend fun <T : RealmObject> insertOrUpdate(data: List<T>?) {
        data?.takeIf { data.isNotEmpty() }?.let {
            try {
                realm.write {
                    data.forEach {
                        copyToRealm(it, UpdatePolicy.ALL)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    inline fun <reified T : RealmObject> findAll(query: (Realm) -> RealmQuery<T>): List<T> {
        try {
            return query.invoke(realm).find().copyFromRealm()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return emptyList()
    }

    inline fun <reified T : RealmObject> findAllManaged(query: (Realm) -> RealmQuery<T>): RealmResults<T>? {
        try {
            return query.invoke(realm).find()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    inline fun <reified T : RealmObject> findFirst(query: (Realm) -> RealmQuery<T>): T? {
        try {
            return query.invoke(realm).first().find()?.copyFromRealm()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    inline fun <reified T : RealmObject> findFirstManaged(query: (Realm) -> RealmQuery<T>): T? {
        try {
            return query.invoke(realm).first().find()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    suspend fun <T : RealmObject> deleteSingleObject(obj: T?) {
        obj?.let {
            try {
                realm.write {
                    delete(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun <T : RealmObject> deleteMultipleObjects(objects: List<T>?) {
        objects?.takeIf { it.isNotEmpty() }?.let { tList ->
            realm.write {
                tList.forEach { delete(it) }
            }
        }
    }

    suspend fun <T : RealmObject> deleteByQuery(query: (Realm) -> RealmQuery<T>) {
        try {
            realm.write {
                delete(query.invoke(realm).find())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun <T : RealmObject> deleteByQueryBlocking(query: (Realm) -> RealmQuery<T>) {
        try {
            realm.writeBlocking {
                delete(query.invoke(realm).find())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun deleteAll() {
        try {
            realm.write {
                deleteAll()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}