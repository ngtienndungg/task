package vn.dungnt.nothing

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

var application: MainApplication? = null

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        application = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}