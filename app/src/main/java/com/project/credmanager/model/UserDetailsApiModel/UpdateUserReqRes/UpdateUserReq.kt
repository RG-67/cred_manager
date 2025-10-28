package com.project.credmanager.model.UserDetailsApiModel.UpdateUserReqRes

data class UpdateUserReq(
    val internalId: Int,
    val userOldPhone: Long,
    val userEmail: String,
    val password: String,
    val userPhone: Long
)