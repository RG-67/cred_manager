package com.project.credmanager.userViewModel.UserApiViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.credmanager.model.UserDetailsApiModel.GetAllUserRes
import com.project.credmanager.model.UserDetailsApiModel.GetUserByPhoneRes
import com.project.credmanager.model.UserDetailsApiModel.InsertUserReqRes.InsertUserData
import com.project.credmanager.model.UserDetailsApiModel.InsertUserReqRes.InsertUserReq
import com.project.credmanager.model.UserDetailsApiModel.InsertUserReqRes.InsertUserRes
import com.project.credmanager.model.UserDetailsApiModel.UpdateUserReqRes.UpdateUserReq
import com.project.credmanager.model.UserDetailsApiModel.UpdateUserReqRes.UpdateUserRes
import com.project.credmanager.model.UserDetailsApiModel.UserDetails
import com.project.credmanager.model.UserDetailsApiModel.UserDetailsData
import com.project.credmanager.network.repository.UserDetailsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.Response


class UserDetailsApiViewModel(private val userDetailsRepo: UserDetailsRepo?) : ViewModel() {

    private val _users = MutableLiveData<List<UserDetailsData>>()
    val users: LiveData<List<UserDetailsData>> = _users

    private val _insertedUser = MutableLiveData<InsertUserRes>()
    val insertedUser: LiveData<InsertUserRes> = _insertedUser

    private val _getUserByPhone = MutableLiveData<UserDetails>()
    val getUserByPhone: LiveData<UserDetails> = _getUserByPhone

    private val _updatedUser =
        MutableLiveData<com.project.credmanager.model.UserDetailsApiModel.UpdateUserReqRes.UserDetails>()
    val updatedUser: LiveData<com.project.credmanager.model.UserDetailsApiModel.UpdateUserReqRes.UserDetails> =
        _updatedUser

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getAllUser() {
        viewModelScope.launch {
            val result = userDetailsRepo!!.getAllUser()
            result.onSuccess { res ->
                if (res.status) _users.value = res.data
                else _error.value = res.msg
            }.onFailure { throwable ->
                _error.value = throwable.message ?: "Unknown error from getAllUser response"
            }
        }
    }

    fun insertUser(insertUserReq: InsertUserReq) {
        viewModelScope.launch {
            val result = userDetailsRepo!!.insertUser(insertUserReq)
            result.onSuccess { res ->
                if (res.status) _insertedUser.value = res
                else _error.value = res.msg
            }.onFailure { throwable ->
                _error.value = throwable.message ?: "Unknown error from insert user response"
            }
        }
    }

    fun getUserByPhone(phone: String) {
        viewModelScope.launch {
            val result = userDetailsRepo!!.getUserByPhone(phone)
            result.onSuccess { res ->
                if (res.status) _getUserByPhone.value = res.data
                else _error.value = res.msg
            }.onFailure { throwable ->
                _error.value = throwable.message ?: "Unknown error from getUserByPhone response"
            }
        }
    }

    fun updateUser(updateUserReq: UpdateUserReq) {
        viewModelScope.launch {
            val response = userDetailsRepo!!.updateUser(updateUserReq)
            response.onSuccess { res ->
                if (res.status) _updatedUser.value = res.data
                else _error.value = res.msg
            }.onFailure { throwable ->
                _error.value = throwable.message ?: "Unknown error from updateUser response"
            }
        }
    }

}