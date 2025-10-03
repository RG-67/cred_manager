package com.project.credmanager

import android.app.Application
import com.project.credmanager.db.CredDB


class InitDbApplication : Application() {

    companion object {
        lateinit var credDb: CredDB
            private set
    }

    override fun onCreate() {
        super.onCreate()
        credDb = CredDB.getDatabase(this)
    }

}