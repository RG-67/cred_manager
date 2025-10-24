package com.project.credmanager.model.UserDetailsApiModel.UpdateUserCredReqRes

data class UpdateUserCredReq(
    val description: String,
    val deviceId: String,
    val password: String,
    val title: String,
    val userName: String,
    val userPhone: Long,
    val updated_at: String
)