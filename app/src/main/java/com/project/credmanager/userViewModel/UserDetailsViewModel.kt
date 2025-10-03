package com.project.credmanager.userViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.credmanager.dao.UserDetailsDao
import com.project.credmanager.model.UserDetails
import kotlinx.coroutines.launch

class UserDetailsViewModel(private val userDetailsDao: UserDetailsDao) : ViewModel() {

    fun getAllUser(): List<UserDetails> = userDetailsDao.getAllUser()

    fun insertUser(userDetails: UserDetails) {
        viewModelScope.launch {
            userDetailsDao.insertUser(userDetails)
        }
    }

    fun getSingleUser(id: Long, userId: String) {
        viewModelScope.launch {
            userDetailsDao.getSingleUser(id, userId)
        }
    }

    fun updateUser(userDetails: UserDetails) {
        viewModelScope.launch {
            userDetailsDao.updateUser(userDetails)
        }
    }

}