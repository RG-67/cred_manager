package com.project.credmanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "UserDetails")
data class UserDetails(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "userId")
    val userId: String,
    @ColumnInfo(name = "userPhone")
    val userPhone: Long,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "deviceId")
    val deviceId: String
)