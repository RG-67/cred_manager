package com.project.credmanager.model.UserDetailsApiModel.InsertUserCredReqRes

data class InsertUserCredReq(
    val description: String,
    val deviceId: String,
    val generatedUserId: Int,
    val password: String,
    val title: String,
    val userId: String,
    val userName: String,
    val userPhone: Long,
    val localCredId: Int
)