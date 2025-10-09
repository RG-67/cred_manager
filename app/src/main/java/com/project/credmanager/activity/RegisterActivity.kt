package com.project.credmanager.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.project.credmanager.R
import com.project.credmanager.databinding.ActivityRegisterBinding
import com.project.credmanager.db.CredDB
import com.project.credmanager.model.UserDetails
import com.project.credmanager.userViewModel.UserDetailsViewModel
import com.project.credmanager.userViewModel.UserViewModelFactory
import com.project.credmanager.utils.HandleUserInput
import com.project.credmanager.utils.Loading

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var userDetailsViewModel: UserDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = CredDB.getDatabase(this)
        val userDetailsDao = database.userDetailsDao()
        val factory = UserViewModelFactory(null, userDetailsDao)
        userDetailsViewModel = ViewModelProvider(this, factory)[UserDetailsViewModel::class.java]

        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }

        binding.registerBtn.setOnClickListener {
            handleRegister()
        }

    }

    private fun handleRegister() {
        val phone = binding.phone.text.toString()
        val password = binding.password.text.toString()
        val conPass = binding.conPassword.text.toString()

        val result = HandleUserInput.checkUserInput(false, phone, password, conPass)

        if (result.second) {
            Loading.showLoading(this)
            val userId = "CM" + (1000..9999).random()
            val pass = HandleUserInput.bcryptHash(password)
            val deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
            val userDetails = UserDetails(
                id = 0,
                userId = userId,
                userPhone = binding.phone.text.toString().toLong(),
                password = pass,
                deviceId = deviceId
            )
            Loading.dismissLoading()
            userDetailsViewModel.insertUser(userDetails)
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        } else {
            Snackbar.make(this, binding.root, result.first, Snackbar.LENGTH_SHORT).show()
        }
    }


}