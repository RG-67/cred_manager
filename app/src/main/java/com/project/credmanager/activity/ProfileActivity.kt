package com.project.credmanager.activity

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
import com.google.android.material.snackbar.Snackbar
import com.project.credmanager.R
import com.project.credmanager.databinding.ActivityProfileBinding
import com.project.credmanager.db.CredDB
import com.project.credmanager.model.UserDetails
import com.project.credmanager.userViewModel.UserDetailsViewModel
import com.project.credmanager.userViewModel.UserViewModelFactory
import com.project.credmanager.utils.AppPreference
import com.project.credmanager.utils.HandleUserInput
import com.project.credmanager.utils.Loading

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var userDetailsViewModel: UserDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = CredDB.getDatabase(this)
        val userViewDetailsDao = database.userDetailsDao()
        val factory = UserViewModelFactory(null, userViewDetailsDao)
        userDetailsViewModel = ViewModelProvider(this, factory)[UserDetailsViewModel::class.java]

        binding.userId.text = AppPreference.getUserId(this)
        binding.phone.setText(AppPreference.getUserPhone(this))

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
        val oldPass = binding.oldPassword.text.toString().trim()
        val newPass = binding.newPassword.text.toString().trim()
        val confirmPass = binding.confirmPassword.text.toString().trim()

        val result = HandleUserInput.checkProfileInput(phone, oldPass, newPass, confirmPass)

        if (result.second) {
            userDetailsViewModel.getSingleUser(
                AppPreference.getUserPhone(this)!!.toLong()
            ) { user ->
                val password = user!!.password
                Loading.showLoading(this)
                if (HandleUserInput.verifyPassword(oldPass, password)) {
                    val pass = HandleUserInput.bcryptHash(confirmPass)
                    val userDetails = UserDetails(
                        AppPreference.getGeneratedUserId(this)!!.toLong(),
                        binding.userId.text.toString().trim(),
                        phone.toLong(),
                        pass,
                        AppPreference.getDeviceId(this)!!
                    )
                    userDetailsViewModel.updateUser(userDetails)
                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    AppPreference.setUserPhone(this, phone)
                    Loading.dismissLoading()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Snackbar.make(
                        this,
                        binding.root,
                        "Old password is not correct",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                Loading.dismissLoading()
            }
        } else {
            Snackbar.make(this, binding.root, result.first, Snackbar.LENGTH_SHORT).show()
        }
    }

}