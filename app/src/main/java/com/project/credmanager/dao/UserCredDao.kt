package com.project.credmanager.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.project.credmanager.model.UserCred

@Dao
interface UserCredDao {
    @Query("SELECT * FROM UserCred WHERE id = :userId")
    fun getAllCred(userId: Int): LiveData<List<UserCred>>

    @Insert
    suspend fun insertUserCred(userCred: UserCred)

    @Update
    suspend fun updateUserCred(userCred: UserCred)

    @Delete
    suspend fun deleteCred(userCred: UserCred)
}