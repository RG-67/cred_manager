package com.project.credmanager.utils

object ApiConstants {

    const val BASE_URL = "https://cred-manager-api.onrender.com/app/v1/"

    /*user details api*/
    const val getAllUser = "user/getAllUser"
    const val insertUser = "user/insertUser"
    const val getUserByPhone = "user/getUserByPhone"
    const val updateUser = "user/updateUser"


    /*user cred api*/
    const val getAllCred = "userCred/getAllCred"
    const val insertUserCred = "userCred/insertUserCred"
    const val updateUserCred = "userCred/updateUserCred"
    const val deleteUserCred = "userCred/deleteUserCred"

}