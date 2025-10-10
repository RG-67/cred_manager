package com.project.credmanager.userViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.credmanager.dao.UserDetailsDao
import com.project.credmanager.model.UserDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.security.auth.callback.Callback

class UserDetailsViewModel(private val userDetailsDao: UserDetailsDao?) : ViewModel() {

    suspend fun getAllUser(): List<UserDetails> = userDetailsDao?.getAllUser()!!

    fun insertUser(userDetails: UserDetails) {
        viewModelScope.launch {
            userDetailsDao?.insertUser(userDetails)
        }
    }

    fun getSingleUser(phone: Long, callback: (UserDetails?) -> Unit) {
        viewModelScope.launch {
            try {
                val user = userDetailsDao?.getSingleUser(phone)
                callback(user)
            } catch (e: Exception) {
                e.printStackTrace()
                callback(null)
            }
        }
    }

    fun updateUser(userDetails: UserDetails) {
        viewModelScope.launch {
            userDetailsDao?.updateUser(userDetails)
        }
    }

}