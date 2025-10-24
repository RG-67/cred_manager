package com.project.credmanager.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.project.credmanager.dao.UserCredDao
import com.project.credmanager.dao.UserDetailsDao
import com.project.credmanager.model.UserCred
import com.project.credmanager.model.UserDetails


@Database(version = 2, entities = [UserDetails::class, UserCred::class], exportSchema = true)
abstract class CredDB : RoomDatabase() {
    abstract fun userCredDao(): UserCredDao
    abstract fun userDetailsDao(): UserDetailsDao

    companion object {
        @Volatile
        var INSTANCE: CredDB? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE UserCred ADD COLUMN created_at TEXT NOT NULL DEFAULT ''")
                db.execSQL("ALTER TABLE UserCred ADD COLUMN updated_at TEXT NOT NULL DEFAULT ''")
            }
        }

        fun getDatabase(context: Context): CredDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CredDB::class.java,
                    "user_cred"
                ).addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}