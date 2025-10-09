package com.project.credmanager.userViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.credmanager.dao.UserCredDao
import com.project.credmanager.model.UserCred
import kotlinx.coroutines.launch

class UserViewModel(private val userCredDao: UserCredDao?) : ViewModel() {

    fun getAllUserById(generatedUserId: Int, userId: String): LiveData<List<UserCred>> =
        userCredDao!!.getAllCred(generatedUserId, userId)

    fun insertUserCred(userCred: UserCred) {
        viewModelScope.launch {
            userCredDao?.insertUserCred(userCred)
        }
    }

    fun updateUserCred(userCred: UserCred) {
        viewModelScope.launch {
            userCredDao?.updateUserCred(userCred)
        }
    }

    fun deleteUserCred(userCred: UserCred) {
        viewModelScope.launch {
            userCredDao?.deleteCred(userCred)
        }
    }

}