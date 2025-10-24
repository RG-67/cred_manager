package com.project.credmanager.model.UserDetailsApiModel.UpdateUserReqRes

data class UpdateUserReq(
    val internalId: Int,
    val userOldPhone: Long,
    val password: String,
    val userPhone: Long
)