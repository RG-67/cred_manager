package com.project.credmanager.model.UserDetailsApiModel

data class GetAllCredRes(
    val `data`: List<Credential>,
    val msg: String,
    val status: Boolean
)


data class Credential(
    val description: String,
    val deviceid: String,
    val generateduserid: Int,
    val internal_id: Int,
    val password: String,
    val title: String,
    val userid: String,
    val username: String,
    val userphone: Long
)