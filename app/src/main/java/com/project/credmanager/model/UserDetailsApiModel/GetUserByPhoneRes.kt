package com.project.credmanager.model.UserDetailsApiModel

data class GetUserByPhoneRes(
    val `data`: UserDetails,
    val msg: String,
    val status: Boolean
)

data class UserDetails(
    val deviceid: String,
    val internal_id: Int,
    val password: String,
    val userid: String,
    val userphone: Long,
    val email: String
)