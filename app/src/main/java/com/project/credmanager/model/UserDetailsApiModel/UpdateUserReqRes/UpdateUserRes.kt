package com.project.credmanager.model.UserDetailsApiModel.UpdateUserReqRes

data class UpdateUserRes(
    val `data`: UserDetails,
    val msg: String,
    val status: Boolean
)


data class UserDetails(
    val deviceid: String,
    val id: Int,
    val internal_id: Int,
    val password: String,
    val userid: String,
    val userphone: Long
)