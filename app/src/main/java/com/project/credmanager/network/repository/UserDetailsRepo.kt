package com.project.credmanager.network.repository

import android.util.Log
import com.project.credmanager.model.UserDetailsApiModel.GetAllUserRes
import com.project.credmanager.model.UserDetailsApiModel.GetUserByPhoneRes
import com.project.credmanager.model.UserDetailsApiModel.InsertUserReqRes.InsertUserReq
import com.project.credmanager.model.UserDetailsApiModel.InsertUserReqRes.InsertUserRes
import com.project.credmanager.model.UserDetailsApiModel.UpdateUserReqRes.UpdateUserReq
import com.project.credmanager.model.UserDetailsApiModel.UpdateUserReqRes.UpdateUserRes
import com.project.credmanager.network.ApiInterface
import org.json.JSONObject
import retrofit2.Response

class UserDetailsRepo(private val apiInterface: ApiInterface) {

    suspend fun getAllUser(): Result<GetAllUserRes> {
        return try {
            val response = apiInterface.getAllUser()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                val jsonObject = JSONObject(response.errorBody()?.string().toString())
                Result.failure(Exception(jsonObject.optString("msg")))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun insertUser(insertUserReq: InsertUserReq): Result<InsertUserRes> {
        return try {
            val response = apiInterface.insertUser(insertUserReq)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                val jsonObject = JSONObject(response.errorBody()?.string().toString())
                Result.failure(Exception(jsonObject.optString("msg")))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserByPhone(phone: String): Result<GetUserByPhoneRes> {
        return try {
            val response = apiInterface.getUserByPhone(phone)
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

    suspend fun updateUser(updateUserReq: UpdateUserReq): Result<UpdateUserRes> {
        return try {
            val response = apiInterface.updateUser(updateUserReq)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception(e))
        }
    }

}