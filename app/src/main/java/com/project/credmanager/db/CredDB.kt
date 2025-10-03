package com.project.credmanager.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project.credmanager.dao.UserCredDao
import com.project.credmanager.dao.UserDetailsDao
import com.project.credmanager.model.UserCred
import com.project.credmanager.model.UserDetails


@Database(version = 1, entities = [UserDetails::class, UserCred::class], exportSchema = true)
abstract class CredDB : RoomDatabase() {
    abstract fun userCredDao(): UserCredDao
    abstract fun userDetailsDao(): UserDetailsDao

    companion object {
        @Volatile
        var INSTANCE: CredDB? = null

        fun getDatabase(context: Context): CredDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CredDB::class.java,
                    "user_cred"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}