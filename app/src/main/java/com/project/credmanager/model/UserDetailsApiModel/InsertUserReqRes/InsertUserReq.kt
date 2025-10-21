package com.project.credmanager.model.UserDetailsApiModel.InsertUserReqRes

data class InsertUserReq(
    val deviceId: String,
    val password: String,
    val userId: String,
    val userPhone: String
)