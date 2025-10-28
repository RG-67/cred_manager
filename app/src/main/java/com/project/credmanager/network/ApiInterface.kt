package com.project.credmanager.network

import com.project.credmanager.model.UserDetailsApiModel.DeleteUserCredRes
import com.project.credmanager.model.UserDetailsApiModel.GetAllCredRes
import com.project.credmanager.model.UserDetailsApiModel.GetAllUserRes
import com.project.credmanager.model.UserDetailsApiModel.GetUserByPhoneRes
import com.project.credmanager.model.UserDetailsApiModel.InsertUserCredReqRes.InsertUserCredReq
import com.project.credmanager.model.UserDetailsApiModel.InsertUserCredReqRes.InsertUserCredRes
import com.project.credmanager.model.UserDetailsApiModel.InsertUserReqRes.InsertUserReq
import com.project.credmanager.model.UserDetailsApiModel.InsertUserReqRes.InsertUserRes
import com.project.credmanager.model.UserDetailsApiModel.UpdateUserCredReqRes.UpdateUserCredReq
import com.project.credmanager.model.UserDetailsApiModel.UpdateUserCredReqRes.UpdateUserCredRes
import com.project.credmanager.model.UserDetailsApiModel.UpdateUserReqRes.UpdateUserReq
import com.project.credmanager.model.UserDetailsApiModel.UpdateUserReqRes.UpdateUserRes
import com.project.credmanager.model.UserDetailsApiModel.UserDetailsData
import com.project.credmanager.utils.ApiConstants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import retrofit2.http.QueryMap
import java.math.BigInteger


interface ApiInterface {

    /*User details api*/
    @GET(ApiConstants.getAllUser)
    suspend fun getAllUser(): Response<GetAllUserRes>

    @POST(ApiConstants.insertUser)
    suspend fun insertUser(@Body insertUserReq: InsertUserReq): Response<InsertUserRes>

    @GET(ApiConstants.getUserByPhone)
    suspend fun getUserByPhone(
        @Query("phone") phone: String,
        @Query("email") email: String
    ): Response<GetUserByPhoneRes>

    @PUT(ApiConstants.updateUser)
    suspend fun updateUser(@Body updateUserReq: UpdateUserReq): Response<UpdateUserRes>
    /*-----------------------------  *****  ------------------------------------*/


    /*User cred api*/
    @GET(ApiConstants.getAllCred)
    suspend fun getAllUserCred(
        @Query("generatedUserId") generatedUserId: BigInteger,
        @Query("userId") userId: String
    ): Response<GetAllCredRes>

    @POST(ApiConstants.insertUserCred)
    suspend fun insertUserCred(@Body insertUserCredReq: InsertUserCredReq): Response<InsertUserCredRes>

    @POST(ApiConstants.updateUserCred)
    suspend fun updateUserCred(
        @QueryMap map: HashMap<String, String>,
        @Body updateUserCredReq: UpdateUserCredReq
    ): Response<UpdateUserCredRes>

    @DELETE(ApiConstants.deleteUserCred)
    suspend fun deleteUserCred(@QueryMap map: HashMap<String, String>): Response<DeleteUserCredRes>
    /*-----------------------------  *****  ------------------------------------*/

}