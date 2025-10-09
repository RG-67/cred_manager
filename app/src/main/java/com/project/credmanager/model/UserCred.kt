package com.project.credmanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "UserCred")
data class UserCred(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "generatedUserId")
    val generatedUserId: Int,
    @ColumnInfo(name = "userId")
    val userId: String,
    @ColumnInfo(name = "userPhone")
    val userPhone: Long,
    @ColumnInfo(name = "deviceId")
    val deviceId: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "userName")
    val userName: String,
    @ColumnInfo(name = "Password")
    val password: String,
    @ColumnInfo(name = "Description")
    val description: String
)