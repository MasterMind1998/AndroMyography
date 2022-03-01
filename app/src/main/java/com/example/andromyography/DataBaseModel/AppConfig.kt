package com.example.andromyography.DataBaseModel

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class AppConfig : Application() {
    override fun onCreate() {
        super.onCreate()

        createRealm()
    }

    private fun createRealm() {

        Realm.init(this)

        val config = RealmConfiguration.Builder()
            .allowWritesOnUiThread(true)
            .name("patientDB")
            .schemaVersion(1)
            .build()

        Realm.setDefaultConfiguration(config)
    }
}