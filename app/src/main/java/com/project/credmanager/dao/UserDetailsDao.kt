package com.project.credmanager.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.project.credmanager.model.UserCred
import com.project.credmanager.model.UserDetails

@Dao
interface UserDetailsDao {

    @Query("SELECT * FROM UserDetails")
    fun getAllUser(): List<UserDetails>

    @Insert
    suspend fun insertUser(userDetails: UserDetails)

    @Query("SELECT * FROM UserDetails WHERE id = :id AND userId = :userId")
    suspend fun getSingleUser(id: Long, userId: String): UserDetails


    @Update
    suspend fun updateUser(userDetails: UserDetails)

}