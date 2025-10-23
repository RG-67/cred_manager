package com.project.credmanager.model.UserDetailsApiModel.InsertUserCredReqRes

data class InsertUserCredRes(
    val `data`: UserCred,
    val msg: String,
    val status: Boolean
)

data class UserCred(
    val description: String,
    val deviceid: String,
    val generateduserid: Int,
    val internal_id: Int,
    val password: String,
    val title: String,
    val userid: String,
    val username: String,
    val userphone: Long,
    val localCredId: Int
)