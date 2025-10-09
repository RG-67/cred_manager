package com.project.credmanager.userViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.credmanager.dao.UserCredDao
import com.project.credmanager.dao.UserDetailsDao
import com.project.credmanager.model.UserCred

class UserViewModelFactory(
    private val userCredDao: UserCredDao?,
    private val userDetailsDao: UserDetailsDao?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UserViewModel::class.java) -> UserViewModel(userCredDao) as T
            modelClass.isAssignableFrom(UserDetailsViewModel::class.java) -> UserDetailsViewModel(
                userDetailsDao
            ) as T


            else -> throw IllegalArgumentException("Unknown viewModel class: ${modelClass.name}")
        }
    }
}