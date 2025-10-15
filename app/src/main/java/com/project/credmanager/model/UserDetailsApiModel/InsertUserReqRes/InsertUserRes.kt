package com.project.credmanager.model.UserDetailsApiModel.InsertUserReqRes

data class InsertUserRes(
    val `data`: InsertUserData,
    val msg: String,
    val status: Boolean
)

data class InsertUserData(
    val deviceid: String,
    val id: Int,
    val internal_id: Int,
    val password: String,
    val userid: String,
    val userphone: Long
)