package com.project.credmanager.userViewModel.UserApiViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.credmanager.network.repository.UserCredRepo
import com.project.credmanager.network.repository.UserDetailsRepo


class UserApiViewModelFactory(
    private val userDetailsRepo: UserDetailsRepo?,
    private val userCredRepo: UserCredRepo?
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UserDetailsApiViewModel::class.java) -> UserDetailsApiViewModel(
                userDetailsRepo
            ) as T

            modelClass.isAssignableFrom(UserCredApiViewModel::class.java) -> UserCredApiViewModel(
                userCredRepo
            ) as T

            else -> throw IllegalArgumentException("Unknown viewmodel class")
        }
    }

}