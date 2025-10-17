package com.project.credmanager

import android.app.Application
import com.project.credmanager.db.CredDB
import com.project.credmanager.utils.NetworkMonitor


class InitDbApplication : Application() {

    companion object {
        lateinit var credDb: CredDB
            private set
    }

    override fun onCreate() {
        super.onCreate()
        credDb = CredDB.getDatabase(this)
        NetworkMonitor.registerNetwork(this)
    }

}