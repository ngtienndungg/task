// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply {
        set("hilt_version", "2.48.1")
        set("retrofit_version", "2.9.0")
        set("moshi_version", "1.14.0")
        set("ok_httpclient_version", "4.12.0")
        set("lifecycle_version", "2.6.1")
        set("room_version", "2.6.1")

    }
}

plugins {
    id("com.android.application") version "8.4.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
    id ("io.realm.kotlin") version "1.16.0" apply false
}
