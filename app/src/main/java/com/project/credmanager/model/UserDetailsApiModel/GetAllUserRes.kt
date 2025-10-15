package com.project.credmanager.model.UserDetailsApiModel

data class GetAllUserRes(
    val `data`: List<UserDetailsData>,
    val msg: String,
    val status: Boolean
)

data class UserDetailsData(
    val deviceid: String,
    val id: Int,
    val internal_id: Int,
    val password: String,
    val userid: String,
    val userphone: Long
)