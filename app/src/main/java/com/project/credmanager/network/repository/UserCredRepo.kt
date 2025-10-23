package com.project.credmanager.network.repository

import com.project.credmanager.model.UserDetailsApiModel.DeleteUserCredRes
import com.project.credmanager.model.UserDetailsApiModel.GetAllCredRes
import com.project.credmanager.model.UserDetailsApiModel.InsertUserCredReqRes.InsertUserCredReq
import com.project.credmanager.model.UserDetailsApiModel.InsertUserCredReqRes.InsertUserCredRes
import com.project.credmanager.model.UserDetailsApiModel.UpdateUserCredReqRes.UpdateUserCredReq
import com.project.credmanager.model.UserDetailsApiModel.UpdateUserCredReqRes.UpdateUserCredRes
import com.project.credmanager.network.ApiInterface
import org.json.JSONObject
import retrofit2.Response
import java.math.BigInteger

class UserCredRepo(private val apiInterface: ApiInterface) {

    suspend fun getAllUserCred(
        generatedUserId: BigInteger,
        userId: String
    ): Result<GetAllCredRes> {
        return try {
            val response = apiInterface.getAllUserCred(generatedUserId, userId)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                val jsonObject = JSONObject(response.errorBody()?.string().toString())
                Result.failure(Exception(jsonObject.optString("msg")))
            }
        } catch (e: Exception) {
            Result.failure(Exception(e))
        }
    }

    suspend fun insertUserCred(insertUserCredReq: InsertUserCredReq): Result<InsertUserCredRes> {
        return try {
            val response = apiInterface.insertUserCred(insertUserCredReq)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                val jsonObject = JSONObject(response.errorBody()?.string().toString())
                Result.failure(Exception(jsonObject.optString("msg")))
            }
        } catch (e: Exception) {
            Result.failure(Exception(e))
        }
    }

    suspend fun updateUserCred(
        map: HashMap<String, String>,
        updateUserCredReq: UpdateUserCredReq
    ): Result<UpdateUserCredRes> {
        return try {
            val response = apiInterface.updateUserCred(map, updateUserCredReq)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                val jsonObject = JSONObject(response.errorBody()?.string().toString())
                Result.failure(Exception(jsonObject.optString("msg")))
            }
        } catch (e: Exception) {
            Result.failure(Exception(e))
        }
    }

    suspend fun deleteUserCred(map: HashMap<String, String>): Result<DeleteUserCredRes> {
        return try {
            val response = apiInterface.deleteUserCred(map)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                val jsonObject = JSONObject(response.errorBody()?.string().toString())
                Result.failure(Exception(jsonObject.optString("msg")))
            }
        } catch (e: Exception) {
            Result.failure(Exception(e))
        }
    }


}