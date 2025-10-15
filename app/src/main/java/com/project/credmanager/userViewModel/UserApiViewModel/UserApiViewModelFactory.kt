package com.project.credmanager.userViewModel.UserApiViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.credmanager.network.repository.UserCredRepo
import com.project.credmanager.network.repository.UserDetailsRepo
import com.project.credmanager.userViewModel.UserViewModel
import kotlin.jvm.Throws

class UserApiViewModelFactory(
    private val userDetailsRepo: UserDetailsRepo?,
    private val userCredRepo: UserCredRepo?
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserDetailsViewModel(userDetailsRepo) as T
        } else {
            return UserCredViewModel(userCredRepo) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }

}