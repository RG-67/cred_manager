package com.project.credmanager.model.UserDetailsApiModel.InsertUserReqRes

data class InsertUserReq(
    val deviceId: String,
    val id: String,
    val password: String,
    val userId: String,
    val userPhone: String
)