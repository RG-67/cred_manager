package com.project.credmanager.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.utils.Utils
import com.google.android.material.snackbar.Snackbar
import com.project.credmanager.R
import com.project.credmanager.databinding.ActivityProfileBinding
import com.project.credmanager.db.CredDB
import com.project.credmanager.model.UserDetails
import com.project.credmanager.model.UserDetailsApiModel.UpdateUserReqRes.UpdateUserReq
import com.project.credmanager.network.ApiClient
import com.project.credmanager.network.repository.UserDetailsRepo
import com.project.credmanager.userViewModel.UserApiViewModel.UserApiViewModelFactory
import com.project.credmanager.userViewModel.UserApiViewModel.UserDetailsApiViewModel
import com.project.credmanager.userViewModel.UserDetailsViewModel
import com.project.credmanager.userViewModel.UserViewModelFactory
import com.project.credmanager.utils.AppPreference
import com.project.credmanager.utils.HandleUserInput
import com.project.credmanager.utils.Loading
import com.project.credmanager.utils.NetworkDialog
import com.project.credmanager.utils.NetworkMonitor

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var userDetailsApiViewModel: UserDetailsApiViewModel
    private var networkDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        networkDialog = NetworkDialog.showNetworkDialog(this)

        val apiInterface = ApiClient.apiInterface
        val userDetailsRepo = UserDetailsRepo(apiInterface)
        userDetailsApiViewModel = ViewModelProvider(
            this,
            UserApiViewModelFactory(userDetailsRepo, null)
        )[UserDetailsApiViewModel::class.java]

        binding.userId.text = AppPreference.getUserId(this)
        binding.phone.setText(AppPreference.getUserPhone(this))
        binding.email.setText(AppPreference.getEmailId(this))

        clickMethod()

    }

    private fun clickMethod() {
        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.showPass.setOnClickListener {
            setPassVisibility(binding.oldPassword, binding.showPass)
        }

        binding.newPass.setOnClickListener {
            setPassVisibility(binding.newPassword, binding.newPass)
        }

        binding.conFirmPass.setOnClickListener {
            setPassVisibility(binding.confirmPassword, binding.conFirmPass)
        }

        binding.submitBtn.setOnClickListener {
            updateProfile()
        }

        userDetailsApiViewModel.getUserByPhone.observe(this) { user ->
            val isPassCheck =
                HandleUserInput.verifyPassword(binding.oldPassword.text.toString(), user.password)
            if (isPassCheck) {
                val pass = HandleUserInput.bcryptHash(binding.confirmPassword.text.toString())
                val updateUserReq = UpdateUserReq(
                    internalId = AppPreference.getInternalId(this)!!.toInt(),
                    userOldPhone = AppPreference.getUserPhone(this)!!.toLong(),
                    password = pass,
                    userPhone = binding.phone.text.toString().toLong(),
                    userEmail = binding.email.text.toString()
                )
                userDetailsApiViewModel.updateUser(updateUserReq)
            } else {
                Loading.dismissLoading()
                Snackbar.make(this, binding.root, "Invalid old password", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

        userDetailsApiViewModel.errorUsersByPhone.observe(this) { msg ->
            Snackbar.make(this, binding.root, msg, Snackbar.LENGTH_SHORT).show()
        }

        userDetailsApiViewModel.updatedUser.observe(this) {
            Loading.dismissLoading()
            AppPreference.setUserPhone(this, binding.phone.text.toString())
            Snackbar.make(this, binding.root, "User updated successfully", Snackbar.LENGTH_SHORT)
                .show()
            finish()
        }

        userDetailsApiViewModel.errorUpdateUsers.observe(this) { msg ->
            Loading.dismissLoading()
            Snackbar.make(this, binding.root, msg, Snackbar.LENGTH_SHORT).show()
        }

    }

    private fun setPassVisibility(edtText: EditText, image: ImageView) {
        if (edtText.text.toString().isNotEmpty()) {
            if (edtText.transformationMethod == null) {
                edtText.transformationMethod = PasswordTransformationMethod()
                image.setImageResource(R.drawable.eye_close)
            } else {
                edtText.transformationMethod = null
                image.setImageResource(R.drawable.eye_open)
            }
            edtText.setSelection(edtText.text.length)
        }
    }

    private fun updateProfile() {
        val phone = binding.phone.text.toString().trim()
        val email = binding.email.text.toString()
        val oldPass = binding.oldPassword.text.toString().trim()
        val newPass = binding.newPassword.text.toString().trim()
        val confirmPass = binding.confirmPassword.text.toString().trim()

        val result = HandleUserInput.checkProfileInput(phone, email, oldPass, newPass, confirmPass)

        if (result.second) {
            Loading.showLoading(this)
            userDetailsApiViewModel.getUserByPhone(
                AppPreference.getUserPhone(this).toString(),
                email
            )
        } else {
            Snackbar.make(this, binding.root, result.first, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        NetworkMonitor.isConnected.observe(this) { isConnected ->
            if (isConnected) {
                networkDialog?.dismiss()
            } else {
                networkDialog?.show()
            }
        }
    }

}