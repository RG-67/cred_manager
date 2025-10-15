package com.project.credmanager.network.repository

import com.project.credmanager.model.UserDetailsApiModel.GetAllUserRes
import com.project.credmanager.model.UserDetailsApiModel.GetUserByPhoneRes
import com.project.credmanager.model.UserDetailsApiModel.InsertUserReqRes.InsertUserReq
import com.project.credmanager.model.UserDetailsApiModel.InsertUserReqRes.InsertUserRes
import com.project.credmanager.model.UserDetailsApiModel.UpdateUserReqRes.UpdateUserReq
import com.project.credmanager.model.UserDetailsApiModel.UpdateUserReqRes.UpdateUserRes
import com.project.credmanager.network.ApiInterface
import retrofit2.Response

class UserDetailsRepo(private val apiInterface: ApiInterface) {

    suspend fun getAllUser(): List<GetAllUserRes> {
        return apiInterface.getAllUser()
    }

    suspend fun insertUser(insertUserReq: InsertUserReq): Response<InsertUserRes> {
        return apiInterface.insertUser(insertUserReq)
    }

    suspend fun getUserByPhone(phone: String): Response<GetUserByPhoneRes> {
        return apiInterface.getUserByPhone(phone)
    }

    suspend fun updateUser(updateUserReq: UpdateUserReq): Response<UpdateUserRes> {
        return apiInterface.updateUser(updateUserReq)
    }

}