package com.project.credmanager.network.repository

import com.project.credmanager.model.UserDetailsApiModel.DeleteUserCredRes
import com.project.credmanager.model.UserDetailsApiModel.GetAllCredRes
import com.project.credmanager.model.UserDetailsApiModel.InsertUserCredReqRes.InsertUserCredReq
import com.project.credmanager.model.UserDetailsApiModel.InsertUserCredReqRes.InsertUserCredRes
import com.project.credmanager.model.UserDetailsApiModel.UpdateUserCredReqRes.UpdateUserCredReq
import com.project.credmanager.model.UserDetailsApiModel.UpdateUserCredReqRes.UpdateUserCredRes
import com.project.credmanager.network.ApiInterface
import retrofit2.Response
import java.math.BigInteger

class UserCredRepo(private val apiInterface: ApiInterface) {

    suspend fun getAllUserCred(
        generatedUserId: BigInteger,
        userId: String
    ): Response<GetAllCredRes> {
        return apiInterface.getAllUserCred(generatedUserId, userId)
    }

    suspend fun insertUserCred(insertUserCredReq: InsertUserCredReq): Response<InsertUserCredRes> {
        return apiInterface.insertUserCred(insertUserCredReq)
    }

    suspend fun updateUserCred(
        map: HashMap<String, String>,
        updateUserCredReq: UpdateUserCredReq
    ): Response<UpdateUserCredRes> {
        return apiInterface.updateUserCred(map, updateUserCredReq)
    }

    suspend fun deleteUserCred(map: HashMap<String, String>): Response<DeleteUserCredRes> {
        return apiInterface.deleteUserCred(map)
    }

}