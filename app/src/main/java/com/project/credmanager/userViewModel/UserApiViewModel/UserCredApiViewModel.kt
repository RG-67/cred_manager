package com.project.credmanager.userViewModel.UserApiViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.credmanager.model.UserDetailsApiModel.Credential
import com.project.credmanager.model.UserDetailsApiModel.DeleteUserCredRes
import com.project.credmanager.model.UserDetailsApiModel.InsertUserCredReqRes.InsertUserCredReq
import com.project.credmanager.model.UserDetailsApiModel.InsertUserCredReqRes.UserCred
import com.project.credmanager.model.UserDetailsApiModel.UpdateUserCredReqRes.UpdateUserCredReq
import com.project.credmanager.network.repository.UserCredRepo
import kotlinx.coroutines.launch
import java.math.BigInteger

class UserCredApiViewModel(private val userCredRepo: UserCredRepo?) : ViewModel() {

    private val _allCred = MutableLiveData<List<Credential>>()
    val allCred: LiveData<List<Credential>> = _allCred

    private val _allCredError = MutableLiveData<String>()
    val allCredError: LiveData<String> = _allCredError

    private val _insertedCred = MutableLiveData<UserCred>()
    val insertedCred: LiveData<UserCred> = _insertedCred

    private val _insertedCredError = MutableLiveData<String>()
    val insertedCredError: LiveData<String> = _insertedCredError

    private val _updatedCred =
        MutableLiveData<com.project.credmanager.model.UserDetailsApiModel.UpdateUserCredReqRes.UserCred>()
    val updatedCred: LiveData<com.project.credmanager.model.UserDetailsApiModel.UpdateUserCredReqRes.UserCred> =
        _updatedCred

    private val _updateCredError = MutableLiveData<String>()
    val updateCredError: LiveData<String> = _updateCredError

    private val _deletedCred = MutableLiveData<String>()
    val deletedCred: LiveData<String> = _deletedCred

    private val _deleteCredError = MutableLiveData<String>()
    val deleteCredError: LiveData<String> = _deleteCredError


    fun getAllUserCred(generatedUserId: BigInteger, userId: String) {
        viewModelScope.launch {
            val response = userCredRepo!!.getAllUserCred(generatedUserId, userId)
            response.onSuccess { res ->
                if (res.status) _allCred.value = res.data
                else _allCredError.value = res.msg
            }.onFailure { throwable ->
                _allCredError.value = throwable.message ?: "Unknown error from getAllUserCred response"
            }
        }
    }

    fun insertUserCred(insertUserCredReq: InsertUserCredReq) {
        viewModelScope.launch {
            val response = userCredRepo!!.insertUserCred(insertUserCredReq)
            response.onSuccess { res ->
                if (res.status) _insertedCred.value = res.data
                else _insertedCredError.value = res.msg
            }.onFailure { throwable ->
                _insertedCredError.value = throwable.message ?: "Unknown error from insertUserCred response"
            }
        }
    }

    fun updateUserCred(map: HashMap<String, String>, updateUserCredReq: UpdateUserCredReq) {
        viewModelScope.launch {
            val response = userCredRepo!!.updateUserCred(map, updateUserCredReq)
            response.onSuccess { res ->
                if (res.status) _updatedCred.value = res.data
                else _updateCredError.value = res.msg
            }.onFailure { throwable ->
                _updateCredError.value = throwable.message ?: "Unknown error from updateUserCred response"
            }
        }
    }

    fun deleteCred(map: HashMap<String, String>) {
        viewModelScope.launch {
            val response = userCredRepo!!.deleteUserCred(map)
            response.onSuccess { res ->
                if (res.status) _deletedCred.value = res.msg
                else _deleteCredError.value = res.msg
            }.onFailure { throwable ->
                _deleteCredError.value = throwable.message ?: "Unknown error from deletedCred response"
            }
        }
    }

}