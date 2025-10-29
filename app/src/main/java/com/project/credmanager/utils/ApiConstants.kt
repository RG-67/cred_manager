package com.project.credmanager.utils

object ApiConstants {

    const val BASE_URL = "https://localhost:3000/app/v1/"

    /*user details api*/
    const val getAllUser = "user/getAllUser"
    const val insertUser = "user/insertUser"
    const val getUserByPhone = "user/getUserByPhone"
    const val updateUser = "user/updateUser"
    const val sendOtp = "user/sendOtp"
    const val verifyOtp = "user/verifyOtp"
    const val passwordChange = "user/passwordChange"


    /*user cred api*/
    const val getAllCred = "userCred/getAllCred"
    const val insertUserCred = "userCred/insertUserCred"
    const val updateUserCred = "userCred/updateUserCred"
    const val deleteUserCred = "userCred/deleteUserCred"

}